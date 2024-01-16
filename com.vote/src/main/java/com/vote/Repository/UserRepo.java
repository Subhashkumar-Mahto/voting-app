package com.vote.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vote.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	List<User> findByCandidateId(long id);
	
	User findByemail(String email);
	
	

	
}
