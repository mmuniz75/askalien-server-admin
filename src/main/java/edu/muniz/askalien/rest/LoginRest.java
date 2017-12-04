package edu.muniz.askalien.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginRest {

		
	@RequestMapping(method=RequestMethod.POST,value="/login")
	public User login(@RequestBody User user){
		
		final String USER_GUEST = System.getenv("USER_GUEST");
	    final String USER_GUEST_PASSWORD = System.getenv("USER_GUEST_PASSWORD");
	    final String USER_ADMIN = System.getenv("USER_ADMIN");
	    final String USER_ADMIN_PASSWORD = System.getenv("USER_ADMIN_PASSWORD");
	    
	    if(user.getLogin().equals(USER_ADMIN) && user.getPassword().equals(USER_ADMIN_PASSWORD))
	    	user.setRole("ADMIN");
		
	    if(user.getLogin().equals(USER_GUEST) && user.getPassword().equals(USER_GUEST_PASSWORD))
	    	user.setRole("USER");
	    
		return user;
	}
	
	
}
