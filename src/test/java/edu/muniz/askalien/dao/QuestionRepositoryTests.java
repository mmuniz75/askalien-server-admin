package edu.muniz.askalien.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.muniz.askalien.model.Question;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionRepositoryTests {
	
	@Autowired
	private QuestionRepository repo;
	
	@Test
	public void getQuestionById(){
		final String CONTENT = "<font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333330154419px;\">These planets are here in your own galaxy, a solar system 70 light years away. They are colonies of humanoids, very similar to this one.&nbsp;</span></font><div><font face=\"Arial, Ver";
		final String SUBJECT = "In an earlier video you mentioned there are two other earth like planets getting ready to go thru stages in their development. Where are these planets located? How far along are they in their development?";
		final LocalDate CREATION_DATE = LocalDate.of(2017,8,15); 
		
		Question question = repo.findQuestionById(83755);
		
				
		LocalDate creationDate = question.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(); 
				
		assertTrue(creationDate.isEqual(CREATION_DATE));
		assertEquals("81.193.48.239",question.getIp());
		assertEquals("PORTUGAL",question.getCountry());
		assertEquals("Jesus Vieira",question.getCreator());
		assertEquals("jesusvieira2000@gmail.com",question.getEmail());
		assertEquals("planets",question.getText());
		assertTrue(question.getAnswer().getContent().contains(CONTENT));
		assertEquals(SUBJECT,question.getAnswer().getSubject());
	}
	
	@Test
	public void getQuestionsWithFeedBack(){
		QuestionFilter filter = new QuestionFilter();
		filter.setJustFeedback(true);
		
		List<Question> questions = repo.findAll(filter);
		
		for(Question question:questions)
			assertNotNull(question.getFeedback());
		
	}
	
	@Test
	public void getQuestionsThisMonth(){
		QuestionFilter filter = new QuestionFilter();
		filter.setJustThisMonth(true);
		List<Question> questions = repo.findAll(filter);
		
		List<Question> checkedQuestions = questions.stream().filter(question -> checkDate(question.getCreationDate())).collect(java.util.stream.Collectors.toList());
		
		assertTrue(questions.size() == checkedQuestions.size());
		
	}
	
	@Test
	public void getQuestionsByIP(){
		final String IP = "79.176.94.126";
		QuestionFilter filter = new QuestionFilter();
		filter.setIpFilter(IP);
		
		List<Question> questions = repo.findAll(filter);
		
		for(Question question:questions)
			assertEquals(IP,question.getIp());
		
	}
	
	@Test
	public void getQuestionsFiltered(){
		final String QUESTION = "what should we eat to have a healthy body";
		QuestionFilter filter = new QuestionFilter();
		filter.setQuestion(QUESTION);
		
		List<Question> questions = repo.findAll(filter);
		
		for(Question question:questions)
			assertEquals(QUESTION,question.getText());
		
	}
	
	@Test
	public void getQuestionsByAnswer(){
		QuestionFilter filter = new QuestionFilter();
		filter.setAnswerId(198);
		
		List<Question> questions = repo.findAll(filter);
		
		assertTrue(questions.size()>=378);
		
	}
	
	@Test
	public void getQuestionsByDates(){
		QuestionFilter filter = new QuestionFilter();
		Date startDate = getDate(2012,4,1); 
		Date endDate = getDate(2012,4,30);
		filter.setStartDate(startDate);
		filter.setEndDate(endDate);
		List<Question> questions = repo.findAll(filter);
		assertTrue(questions.size()==46);
		
	}
	
	@Test
	public void getQuestionsEndDate(){
		QuestionFilter filter = new QuestionFilter();
		Date endDate = getDate(2012,3,31);
		filter.setEndDate(endDate);
		List<Question> questions = repo.findAll(filter);
		assertTrue(questions.size()==201);
		
	}
	
	@Test
	public void getQuestionsStartDate(){
		QuestionFilter filter = new QuestionFilter();
		Date startDate = getDate(2017,10,1);
		filter.setStartDate(startDate);
		List<Question> questions = repo.findAll(filter);
		assertTrue(questions.size()>=2000);
		
	}
	
	private boolean checkDate(Date date1){
		LocalDate now = LocalDate.now();
		Date date2 = getDate(now.getYear(),now.getMonthValue(),1);
		return date1.after(date2) || date1.compareTo(date2)==0;
	}
	
	private Date getDate(int year,int month,int day){
		return Date.from(LocalDateTime.of(year,month,day,0,0,0).atZone(ZoneId.systemDefault()).toInstant());
	}
	
	@Test
	public void getFrequenceUsers(){
		List<Long> questions = repo.findFrequentUsers();
		assertTrue(questions.size()>=1400);
	}
	
	@Test
	public void getCountUsers(){
		Number count = repo.findCountUsers();
		assertTrue(count.longValue()>=4800);
	}
	
	@Test
	public void getCountCountries(){
		Number count = repo.findCountCountries();
		assertTrue(count.longValue()>=100);
	}

}
