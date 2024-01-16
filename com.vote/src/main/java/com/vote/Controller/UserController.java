package com.vote.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.vote.Entity.Candidate;
import com.vote.Entity.Location;
import com.vote.Entity.User;
import com.vote.Service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}
	static boolean loginNotFound = false;
	static boolean loginNotMatch = false;
	static boolean loginError = false;
	static boolean successfullRegister=false;
	static boolean register=false;
	static boolean inputError=false;
	
	@GetMapping("/")
	public String dashBoard(Model model){
		return "index";
	}
	
	@GetMapping("/login")
	public String listUser(Model model){
		User user=new User();
		register =false;
		inputError =false;
		model.addAttribute("user", user);
		model.addAttribute("loginNotFound", loginNotFound);
		model.addAttribute("loginError", loginError);
		model.addAttribute("loginNotMatch", loginNotMatch);
		return "login";
	}
	
	
	@GetMapping("/login/new")
	public String register(Model model) {
		User user=new User();
		model.addAttribute("user", user);
		model.addAttribute("registerFlag", register);
		model.addAttribute("inputError", inputError);
		loginNotFound = false;
		loginNotMatch = false;
		loginError = false;;
		return "register";
	}
	
	@PostMapping("/register")
	public String saveUser(@ModelAttribute User user,Model model) {
		String msg=null;
		register =false;
		inputError =false;
		loginNotFound = false;
		loginNotMatch = false;
		loginError = false;
		msg=userService.checkUserforRegister(user);
		model.addAttribute(msg);
		if(msg=="Register") {
			userService.saveUser(user);
			return "redirect:/login";
		}
		if(msg=="Error") {
			inputError =true;
			return "redirect:/login/new";
		}
		if(msg=="Allredy_register") {
			register =true;
			return "redirect:/login/new";
		}
		return "redirect:/login";
	}
	
	@PostMapping("/checkUser")
    public String checkUser(Model model,@ModelAttribute User user, HttpSession session) {
		String msg = null;
		loginNotFound = false;
		loginNotMatch = false;
		loginError = false;
		boolean checkNull = userService.checkInput(user);
		System.out.println("checkNull ::: "+checkNull);
		if(checkNull) {
			msg = userService.checkUser(user);
		}else {
			loginError = true;
			return "redirect:/login";
		}
		model.addAttribute(msg);
		if(msg=="SUCCESS") {
			List<Candidate> data = userService.getCandiadeteDetails();
			model.addAttribute("candidate", data);
			session.setAttribute("sessionUser", user);
			return "votePage";
		}
		if(msg =="ALD_VOTED") {
			return "AllreadyVoted"; //Chnage page to Arlerady Voted
		}
		if(msg=="ERR_NOT_FOUND") {
			loginNotFound = true;
			return "redirect:/login";
		}
		if(msg=="NOT_MATCHED") {
			loginNotMatch = true;
			return "redirect:/login";
		}
        return "redirect:/login";
	}
//	@GetMapping("/Success")
//	public String saveVote(Model model) {
//	    System.out.println("Success");
//	    return "Success";
//	}
	
//	@PostMapping("/Success/{id}")
//	public String updateCustomer(@PathVariable Long id,
//			@ModelAttribute("candidate") Candidate candidate,
//			Model model) {
//		System.out.println("id:::::::::::::"+id);
//		Candidate updatecandidate = userService.findbyid(id);
//		updatecandidate.setId(id);
//		updatecandidate.setVote_count(candidate.getVote_count());
//		return "redirect:/login";
//		}
	
	@GetMapping("/Success")
	public String voteFor(@RequestParam Long id, HttpSession session , Model model) {
		System.out.println("id ---"+id);
		model.addAttribute("user", new Location());
		User user = (User) session.getAttribute("sessionUser");
		System.out.println("session User :: "+user.getUsername());
		userService.updateCandiateCount(id);
		 //User user2 = new User();
        // user.setDateTime(LocalDateTime.now());
        
         //System.out.println("datetime"+LocalDateTime.now());
         //userService.saveUser(user);
		userService.updateUser(user,id);
		return "Success";
	}
	
	@GetMapping("/ListofUser")
	public String ListofUser(@RequestParam Long id, HttpSession session , Model model) {
		System.out.println("id ---"+id);
		List<User> user=userService.getUserName(id);
		System.out.println("user ::: "+user);
		model.addAttribute("user", user);
		return "ListOfVoteUser";
	}

	@GetMapping("/loginAdmin")
	public String loginAdmin(Model model) {
		User user=new User();
		model.addAttribute("user", user);
		return "loginAdmin";
	}
	
	@GetMapping("/admin")
	public String getadmin(Model model) {
		model.addAttribute("candidate", userService.getCandiadeteDetails());
		return "Admin";
	}
	
	@PostMapping("/checkAdminUser")
    public String checkAdminUser(Model model,@ModelAttribute User user, HttpSession session) {
		String msg = null;
		boolean checkNull = userService.checkInput(user);
		System.out.println("checkNull ::: "+checkNull);
		if(checkNull) {
			msg = userService.checkAdminUser(user);
		}else {
			return "redirect:/loginAdmin";
		}
		if(msg=="SUCCESS") {
			return "redirect:/admin";
		}
		return "redirect:/loginAdmin";
	}
	
	@GetMapping("/map")
    public String showMap(@RequestParam("latitude") Double latitude,@RequestParam("longitude") Double longitude, Long id, Model model) {
		model.addAttribute("latitude", latitude);
        model.addAttribute("longitude", longitude);
        System.out.println("latitude"+latitude);
        System.out.println("longitude"+longitude);
        return "map"; 
    }
	
	   public String handleMissingServletRequestParameter(MissingServletRequestParameterException ex, Model model) {
	        model.addAttribute("error", "Missing or invalid parameter: " + ex.getParameterName());
	        return "error"; // Return the name of your custom error page
	    }

}
