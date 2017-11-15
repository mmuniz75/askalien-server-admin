package edu.muniz.askalien.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.muniz.askalien.dao.AnswerRepository;
import edu.muniz.askalien.dao.AnswerSummary;
import edu.muniz.askalien.dao.QuestionRepository;
import edu.muniz.askalien.model.Answer;
import edu.muniz.askalien.model.Question;
import edu.muniz.askalien.model.Video;
import edu.muniz.askalien.util.IndexingHelper;



@RunWith(SpringRunner.class)
@SpringBootTest
public class AnswerServiceTests {
	
	@Autowired
	AnswerService service;
	
	@Autowired
	IndexingHelper indexing;
	
	@Autowired
	private AnswerRepository repo;
	
	@Autowired
	private QuestionRepository questionRepo;
	
	@Test
	public void testGetAllAnswers(){
		List<AnswerSummary> anwers = service.getAnswers();
		assertTrue(anwers.size()>=1209);
				
		AnswerSummary answer = anwers.get(0);		
		assertNotNull(answer.getSubject());
		
	}
	
	@Test
	public void testGetAnswerFullById(){
		Answer answer = service.getAnswer(24);
		
		final String CONTENT = "<font face=\"Arial, Verdana\"><span style=\"font-size: 13.3333330154419px;\">Yes, in the Community Galactica there are about 30 galactic races of humanoids with this approximate size.</span></font><div><font face=\"Arial, Verdana\"><span style=\"font-size: 13.333";
		final String SUBJECT = "There is a video showing the autopsy of a very small humanoid just over a foot tall. The proportions of his body was like that of an earthlings.  Was this video fake or do these beings exist?";
		final String URL = "https://www.youtube.com/watch?v=rsAXYOc2aPM";
				
		assertEquals(SUBJECT,answer.getSubject());
		assertTrue(answer.getContent().contains(CONTENT));
		assertEquals(URL,answer.getUrl());
				
		assertTrue(answer.getVideo().getNumber()==3);
		
	}
	
	@Test
	public void testGetAnswerById(){
		AnswerSummary answer = service.getAnswerSummary(24);
		
		final String SUBJECT = "There is a video showing the autopsy of a very small humanoid just over a foot tall. The proportions of his body was like that of an earthlings.  Was this video fake or do these beings exist?";
		assertEquals(SUBJECT,answer.getSubject());
		
	}
	
	@Test
	public void testAddAnswer(){
		
		Integer id = null;
		Integer docId = null;
		
		try{
			final String SUBJECT = "struts velociry jsp jsf";
			final String CONTENT = "oracle msqsl sybase sqlserver";
			
			final Video VIDEO = new Video();
			VIDEO.setId(1);
			VIDEO.setNumber(1);
			
			Answer answer = new Answer();
			answer.setContent(CONTENT);
			answer.setSubject(SUBJECT);
			answer.setVideo(VIDEO);
			service.save(answer);
			id = answer.getId();
			answer = null;
			
			Map<Integer,Float> search = indexing.getIdsFromSearch("'" + SUBJECT + "'");
			Set<Integer> ids = search.keySet(); 
			
			docId = ids.iterator().next();
			answer = repo.findAnswerById(docId);
			
			assertEquals(SUBJECT,answer.getSubject());
			assertEquals(CONTENT,answer.getContent());
			
			search = indexing.getIdsFromSearch("'" + CONTENT + "'");
			ids = search.keySet(); 
			
			docId = ids.iterator().next();
			answer = repo.findAnswerById(docId);
			
			assertEquals(SUBJECT,answer.getSubject());
			assertEquals(CONTENT,answer.getContent());
			
		}finally{
			if(id!=null)
				repo.delete(id);
			if(docId!=null)
				indexing.removeObject(docId);
		}
		
	}
	
	@Test
	public void testUpdateAnswer(){
		
		Integer id = null;
		Integer docId = null;
		
		try{
			final String SUBJECT = "struts velociry jsp jsf";
			final String CONTENT = "oracle msqsl sybase sqlserver";
			final String SUBJECT_UPDATED = "angular react vue timeleaf";
			final String CONTENT_UPDATED = "mysql mongodb postgres mariadb";
						
			final Video VIDEO = new Video();
			VIDEO.setId(1);
			VIDEO.setNumber(1);
			
			Answer answer = new Answer();
			answer.setContent(CONTENT);
			answer.setSubject(SUBJECT);
			answer.setVideo(VIDEO);
			service.save(answer);
			id = answer.getId();
			answer = null;
			
			answer = repo.findAnswerById(id);
			answer.setContent(CONTENT_UPDATED);
			answer.setSubject(SUBJECT_UPDATED);
			service.update(answer);
						
			Map<Integer,Float> search = indexing.getIdsFromSearch("'" + SUBJECT + "'");
			assertTrue(search.isEmpty());
			
			search = indexing.getIdsFromSearch("'" + SUBJECT_UPDATED + "'");
			Set<Integer> ids = search.keySet(); 
			
			docId = ids.iterator().next();
			answer = repo.findAnswerById(docId);
			
			assertEquals(SUBJECT_UPDATED,answer.getSubject());
			assertEquals(CONTENT_UPDATED,answer.getContent());
			
			search = indexing.getIdsFromSearch("'" + CONTENT + "'");
			assertTrue(search.isEmpty());
			
			search = indexing.getIdsFromSearch("'" + CONTENT_UPDATED + "'");
			
			ids = search.keySet(); 
			
			docId = ids.iterator().next();
			answer = repo.findAnswerById(docId);
			
			assertEquals(SUBJECT_UPDATED,answer.getSubject());
			assertEquals(CONTENT_UPDATED,answer.getContent());
			
		}finally{
			if(id!=null)
				repo.delete(id);
			if(docId!=null)
				indexing.removeObject(docId);
		}
		
	}
	
	@Test
	public void getTopAnswers(){
		List<Answer> topAnswers = service.getTopAnswers(false);
		assertTrue(topAnswers.get(0).getClicks()>300);
		
		for(int i=1;i<topAnswers.size();i++)
			assertTrue(topAnswers.get(i-1).getClicks()>=topAnswers.get(i).getClicks());
		
		List<Question> questions = questionRepo.findByAnswerIdAndFeedbackIsNull(topAnswers.get(topAnswers.size()-1).getId());
		assertTrue(questions.size()>0);
			
	}
	
	@Test
	public void getTopAnswersFeedBack(){
		List<Answer> topAnswers = service.getTopAnswers(true);
		assertTrue(topAnswers.get(0).getClicks()>=5);
		
		for(int i=1;i<topAnswers.size();i++)
			assertTrue(topAnswers.get(i-1).getClicks()>=topAnswers.get(i).getClicks());
		
		List<Question> questions = questionRepo.findByAnswerIdAndFeedbackIsNotNull(topAnswers.get(topAnswers.size()-1).getId());
		assertTrue(questions.size()>0);
			
	}
	
	@Test
	public void getCountAnswers(){
		Number countAnswers = service.getCountAnswers();
		assertTrue(countAnswers.longValue()>=1200);
			
	}

}
