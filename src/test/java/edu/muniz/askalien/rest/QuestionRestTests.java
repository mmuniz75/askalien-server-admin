package edu.muniz.askalien.rest;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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

import edu.muniz.askalien.dao.QuestionFilter;
import edu.muniz.askalien.dao.QuestionRepository;
import edu.muniz.askalien.model.Question;
import edu.muniz.askalien.util.Util;



@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionRestTests {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	@Autowired
	private QuestionRepository repo;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void testGetQuestions() throws Exception{
		String URL="/questions";
				
		this.mvc.perform(get(URL))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].id", notNullValue()))
			.andExpect(jsonPath("$[0].ip", notNullValue()))
			.andExpect(jsonPath("$[0].country", notNullValue()))
			.andExpect(jsonPath("$[0].date", notNullValue()))
			.andExpect(jsonPath("$[0].text", notNullValue()))
			;
		
	}
	
	@Test
	public void testGetQuestion() throws Exception{
		final String CONTENT = "<font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333330154419px;\">These planets are here in your own galaxy, a solar system 70 light years away. They are colonies of humanoids, very similar to this one.&nbsp;</span></font><div><font face=\"Arial, Ver";
		final String SUBJECT = "In an earlier video you mentioned there are two other earth like planets getting ready to go thru stages in their development. Where are these planets located? How far along are they in their development?";
		String URL="/question/83755";
				
		this.mvc.perform(get(URL))
			.andExpect(status().isOk())
			.andExpect(jsonPath("ip", is("81.193.48.239")))
			.andExpect(jsonPath("country", is("PORTUGAL")))
			.andExpect(jsonPath("date", is("08/15/2017 09:36")))
			.andExpect(jsonPath("text", is("planets")))
			.andExpect(jsonPath("email", is("jesusvieira2000@gmail.com")))
			.andExpect(jsonPath("feedback", is("Can I be transfered?")))
			.andExpect(jsonPath("creator", is("Jesus Vieira")))
			.andExpect(jsonPath("answer.question", is(SUBJECT)))
			.andExpect(jsonPath("answer.content", startsWith(CONTENT)))
		;
				
	}
	
	
	
	@Test
	public void getQuestionsWithFeedBack() throws Exception{
		String URL="/questions";
		
		QuestionFilter filter = new QuestionFilter();
		filter.setJustFeedback(true);
		String requestJson = Util.getJson(filter);
		
		this.mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
				                  .content(requestJson))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].feedback", notNullValue()))
			.andExpect(jsonPath("$[1].feedback", notNullValue()))
			.andExpect(jsonPath("$[2].feedback", notNullValue()))
			.andExpect(jsonPath("$[3].feedback", notNullValue()))
		;
		
	}
	
	@Test
	public void getQuestionsByIP() throws Exception{
		final String IP = "79.176.94.126";
		QuestionFilter filter = new QuestionFilter();
		filter.setIpFilter(IP);
		String requestJson = Util.getJson(filter);
		
		String URL="/questions";
		this.mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))	
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].ip", is(IP)))
				.andExpect(jsonPath("$[1].ip", is(IP)))
				;
	}
	
	
	@Test
	public void getQuestionsFiltered() throws Exception{
		final String QUESTION = "what should we eat to have a healthy body";
		QuestionFilter filter = new QuestionFilter();
		filter.setQuestion(QUESTION);
		String requestJson = Util.getJson(filter);
		
		String URL="/questions";
		this.mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))	
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].text", is(QUESTION)))
				.andExpect(jsonPath("$[1].text", is(QUESTION)))
				.andExpect(jsonPath("$[2].text", is(QUESTION)))
				;
		
	}
	
	@Test
	public void getQuestionsByAnswer() throws Exception{
		QuestionFilter filter = new QuestionFilter();
		filter.setAnswerId(198);
		String requestJson = Util.getJson(filter);
		
		List<Question> questions = repo.findAll(filter);
		int count = questions.size();
		int last = count-1;
		
		String URL="/questions";
		this.mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))	
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(count)))
				.andExpect(jsonPath("$[0].text", is(questions.get(0).getText())))
				.andExpect(jsonPath("$[" + last  +"].text", is(questions.get(last).getText())))
				;
		
	}
	
	@Test
	public void getQuestionsByDates() throws Exception{
		QuestionFilter filter = new QuestionFilter();
		Date startDate = getDate(2012,4,1); 
		Date endDate = getDate(2012,4,30);
		filter.setStartDate(startDate);
		filter.setEndDate(endDate);
	
		String requestJson = Util.getJson(filter);
		
		String URL="/questions";
		this.mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))	
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(46)))
				;
		
	}
	
	

	private Date getDate(int year,int month,int day){
		return Date.from(LocalDateTime.of(year,month,day,0,0,0).atZone(ZoneId.systemDefault()).toInstant());
	}
}
