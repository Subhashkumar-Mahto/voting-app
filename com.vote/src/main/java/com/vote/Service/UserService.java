package com.vote.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vote.Entity.Candidate;
import com.vote.Entity.Location;
import com.vote.Entity.User;
import com.vote.Repository.CandiadateRepo;
import com.vote.Repository.UserRepo;

@Service
public class UserService {

	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	
	@Autowired
	private UserRepo userrepo;
	
	@Autowired
	private CandiadateRepo candiadateRepo;

	public UserService(UserRepo userrepo,CandiadateRepo candiadateRepo) {
		this.userrepo = userrepo;
		this.candiadateRepo=candiadateRepo;
	}
	
//   public List<User> saveDetails() {
//		
//		return userrepo.findAll();
//	}
//   
    public User saveUser(User user) {
    	user.setUserType("user");
		return userrepo.save(user);
	}
    
	public User findbyusername(String username) {
		
		return userrepo.findByUsername(username);
	}
	
//	public User findbyiduser(long id) {
//		return userrepo.getById(id);
//	}
	

	@SuppressWarnings("unused")
	public String checkUser(User user) {
		User dbuser = userrepo.findByUsername(user.getUsername());
		if(null==dbuser) {
			return "ERR_NOT_FOUND";
		}
		System.out.println("dbuser.isHasVoted() ::: "+dbuser.isHasVoted());
		if(null!=dbuser) {
			if(dbuser.getPassword().equalsIgnoreCase(user.getPassword())) {
				if(dbuser.getUserType().equalsIgnoreCase("admin")) {
					return "ADMIN";
				}
				if(dbuser.isHasVoted()) {
					return "ALD_VOTED";
				}
				return "SUCCESS";
			}else {
				return "NOT_MATCHED";
			}
		}
		return "ERROR";
	}

	public boolean checkInput(User user) {
		if(!user.getUsername().trim().equalsIgnoreCase("") && !user.getPassword().trim().equalsIgnoreCase("")) {
			return true;
		}else {
			return false;
		}
	}

	public List<Candidate> getCandiadeteDetails() {
		return candiadateRepo.findAll();
	}
	
//	public Candidate findbyid(long id) {
//		return candiadateRepo.getById(id);
//	}

	  public Candidate saveCandidate(Candidate candidate) {
			
			return candiadateRepo.save(candidate);   
		}

	public void updateCandiateCount(Long id) {
		Optional<Candidate> cand = candiadateRepo.findById(id);
		cand.get().setVoteCount(cand.get().getVoteCount()+1);
		candiadateRepo.save(cand.get());
		
	}

	public void updateUser(User user, Long id) {
		User dbuser = userrepo.findByUsername(user.getUsername());
		dbuser.setHasVoted(true);
		dbuser.setCandidateId(id);
		dbuser.setDateTime(LocalDateTime.now());
		userrepo.save(dbuser);
		
	}

	public List<User> getUserName(Long id) {
		List<User> userList = userrepo.findByCandidateId(id);
		return userList;
	}

	
	public String checkUserforRegister(User user) {
		if(user.getUsername().trim().equalsIgnoreCase("") || user.getPassword().trim().equalsIgnoreCase("") || user.getEmail().trim().equalsIgnoreCase("") || user.getMobile().trim().equalsIgnoreCase("")) {
			return "Error";
		}
		User dbuser = userrepo.findByemail(user.getEmail());
		if(null==dbuser) {
			return "Register";
		}
		return "Allredy_register";
	}

	public String checkAdminUser(User user) {
		User dbuser = userrepo.findByUsername(user.getUsername());
		if(null==dbuser) {
			return "ERR_NOT_FOUND";
		}

		if(null!=dbuser) {
			if(dbuser.getPassword().equalsIgnoreCase(user.getPassword())) {
				if(dbuser.getUserType().equalsIgnoreCase("admin")) {
					return "SUCCESS";
				}
			}else {
				return "NOT_MATCHED";
			}
		}
		return "ERROR";
	}
}
