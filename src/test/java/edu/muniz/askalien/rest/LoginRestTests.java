package edu.muniz.askalien.rest;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.isNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.muniz.askalien.dao.AnswerRepository;
import edu.muniz.askalien.model.Answer;
import edu.muniz.askalien.model.Video;
import edu.muniz.askalien.service.AnswerService;
import edu.muniz.askalien.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginRestTests {
	
		
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
		
	@Test
	public void testUserLogin() throws Exception{
		final String USER_GUEST = System.getenv("USER_GUEST");
	    final String USER_GUEST_PASSWORD = System.getenv("USER_GUEST_PASSWORD");
	    
		String URL="/login";
		
		User user = new User();
		user.setLogin(USER_GUEST);
		user.setPassword(USER_GUEST_PASSWORD);
		
		String userJson = Util.getJson(user);
		
		this.mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
				                   .content(userJson))	
									.andExpect(status().isOk())
									.andExpect(jsonPath("login", is(USER_GUEST)))
									.andExpect(jsonPath("password", is(USER_GUEST_PASSWORD)))
									.andExpect(jsonPath("role", is("USER")))
		;
	
	}
	
		
	@Test
	public void testUserAdmin() throws Exception{
	    final String USER_ADMIN = System.getenv("USER_ADMIN");
	    final String USER_ADMIN_PASSWORD = System.getenv("USER_ADMIN_PASSWORD");
	    
		String URL="/login";
		
		User user = new User();
		user.setLogin(USER_ADMIN);
		user.setPassword(USER_ADMIN_PASSWORD);
		
		String userJson = Util.getJson(user);
		
		this.mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
				                   .content(userJson))	
									.andExpect(status().isOk())
									.andExpect(jsonPath("login", is(USER_ADMIN)))
									.andExpect(jsonPath("password", is(USER_ADMIN_PASSWORD)))
									.andExpect(jsonPath("role", is("ADMIN")))
		;
	
	}
	
}
