package edu.muniz.askalien.rest;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
public class AnswerRestTests {
	
	@Autowired
	private AnswerRepository repo;
	
	@Autowired
	private AnswerService service;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void getAnswers() throws Exception{
		final String SUBJECT1 = "Is the planet Earth is undergoing a transformation in the near future?";
		final String SUBJECT1209 = "Could you tell us if the drilling in the salt domes in the Gulf of Mexico are causing the massive movement of the New Madrid Fault zone? How has the Fracking industry impacted fracture zones? How is this affecting Yellowstone? What are your thoughts of these studies of methane gas?";
		
		String URL="/answers";
		
		this.mvc.perform(get(URL))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id", is(1)))
			.andExpect(jsonPath("$[0].subject", is(SUBJECT1)))
			.andExpect(jsonPath("$[1208].id", is(1209)))
			.andExpect(jsonPath("$[1208].subject", is(SUBJECT1209)))
		;
		
	}
	
	@Test
	public void getTopAnswersNoFeedBack() throws Exception{
		getTopAnswers(false);
	}
	
	@Test
	public void getTopAnswersFeedBack() throws Exception{
		getTopAnswers(true);
	}

	private void getTopAnswers(Boolean feedback) throws Exception{
		
		String URL="/topanswers?feedback=" + feedback;
		
		List<Answer>answers = service.getTopAnswers(feedback);
		int count = answers.size();
		int last = count - 1;
		
		this.mvc.perform(get(URL))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(count)))
			.andExpect(jsonPath("$[0].number", is(answers.get(0).getId())))
			.andExpect(jsonPath("$[0].question", is(answers.get(0).getSubject())))
			.andExpect(jsonPath("$[0].clicks", is(answers.get(0).getClicks().intValue())))
			.andExpect(jsonPath("$[" + last + "].number", is(answers.get(last).getId())))
			.andExpect(jsonPath("$[" + last + "].question", is(answers.get(last).getSubject())))
			.andExpect(jsonPath("$[" + last + "].clicks", is(answers.get(last).getClicks().intValue())))
		;
		
	}
	
	@Test
	public void getAnswer() throws Exception{
		final String SUBJECT = "Where did the humans on Earth orginate from?";
		final String CONTENT = "<font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333330154419px;\">All the different ethnic groups found today on Earth came from different places of the universe.</span></font>";
				
		String URL="/answer/2";
		
		this.mvc.perform(get(URL))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("number", is(2)))
			.andExpect(jsonPath("question", is(SUBJECT)))
			.andExpect(jsonPath("content", startsWith(CONTENT)))
			.andExpect(jsonPath("date", is("09/10/2010")))
			.andExpect(jsonPath("video.number", is(1)))
		;
		
	}
	
	@Test
	public void getAnswerSummary() throws Exception{
		final String SUBJECT = "Where did the humans on Earth orginate from?";
				
		String URL="/summary-answer/2";
		
		this.mvc.perform(get(URL))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("id", is(2)))
			.andExpect(jsonPath("subject", is(SUBJECT)))
		;
		
	}
	
	@Test
	public void testUpdateAnswer() throws Exception{
		
		Integer id = null;
		
		try{
			final String SUBJECT = "sample question";
			final String CONTENT = "we dont have answer for that";
			final String URL = "www.youyube.com.br";
			final Video VIDEO = new Video();
			VIDEO.setId(1);
			VIDEO.setNumber(1);
			
			final String SUBJECT_UDATED = "other question";
			final String CONTENT_UDATED = "for this question we have answer";
			final String URL_UDATED = "www.google.com.br";
			final Video VIDEO_UDATED = new Video();
			VIDEO_UDATED.setId(2);
			VIDEO_UDATED.setNumber(2);
						
			Answer answer = new Answer();
			answer.setContent(CONTENT);
			answer.setSubject(SUBJECT);
			answer.setUrl(URL);
			answer.setVideo(VIDEO);
			
			repo.save(answer);
			id = answer.getId();
			
			answer = null;
			
			answer = repo.findAnswerById(id);
			answer.setContent(CONTENT_UDATED);
			answer.setSubject(SUBJECT_UDATED);
			answer.setUrl(URL_UDATED);
			answer.setVideo(VIDEO_UDATED);
			
			String URL2="/answer";
			String requestJson = Util.getJson(answer);
			
			this.mvc.perform(put(URL2).contentType(MediaType.APPLICATION_JSON)
					                   .content(requestJson))	
										.andExpect(status().isOk())
			;
						
			
			answer = null;
			answer = repo.findAnswerById(id);
						
			assertEquals(SUBJECT_UDATED,answer.getSubject());
			assertEquals(CONTENT_UDATED,answer.getContent());
			assertEquals(URL_UDATED,answer.getUrl());
			assertTrue(VIDEO_UDATED.getNumber()==answer.getVideo().getNumber());
		}finally{
			if(id!=null)
				service.removeAnswer(id);
		}
		
	}
	
	@Test
	public void testAddAnswer() throws Exception{
		
		Integer id = null;
		
		try{
			final String SUBJECT = "sample question";
			final String CONTENT = "we dont have answer for that";
			final String URL = "www.www.www.www";
			final Video VIDEO = new Video();
			VIDEO.setId(1);
			VIDEO.setNumber(1);
			
			Answer answer = new Answer();
			answer.setContent(CONTENT);
			answer.setSubject(SUBJECT);
			answer.setUrl(URL);
			answer.setVideo(VIDEO);
			
						
			String URL2="/answer";
			String requestJson = Util.getJson(answer);
			
			this.mvc.perform(post(URL2).contentType(MediaType.APPLICATION_JSON)
					                   .content(requestJson))	
										.andExpect(status().isOk())
			;
			
			answer = null;
			
			answer = repo.findByUrl(URL);
			id = answer.getId();
			
			assertEquals(SUBJECT,answer.getSubject());
			assertEquals(CONTENT,answer.getContent());
			assertEquals(URL,answer.getUrl());
			assertTrue(VIDEO.getNumber()==answer.getVideo().getNumber());
		}finally{
			if(id!=null)
				service.removeAnswer(id);
		}
		
	}
}
