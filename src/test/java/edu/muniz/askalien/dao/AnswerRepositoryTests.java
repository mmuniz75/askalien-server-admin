package edu.muniz.askalien.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.muniz.askalien.model.Answer;
import edu.muniz.askalien.model.Video;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AnswerRepositoryTests {
	
	@Autowired
	private AnswerRepository repo;

	@Test
	public void testFindAnwer(){
		Answer answer = repo.findOne(1);
		assertEquals(answer.getSubject(),"Is the planet Earth is undergoing a transformation in the near future?");
	}
	
	@Test
	public void testAddAnswer(){
		
		Integer id = null;
		
		try{
			final String SUBJECT = "sample question";
			final String CONTENT = "we dont have answer for that";
			final String URL = "www.youyube.com.br";
			final Video VIDEO = new Video();
			VIDEO.setId(1);
			VIDEO.setNumber(1);
			
			Answer answer = new Answer();
			answer.setContent(CONTENT);
			answer.setSubject(SUBJECT);
			answer.setUrl(URL);
			answer.setVideo(VIDEO);
			
			repo.save(answer);
			id = answer.getId();
			
			answer = null;
			
			answer = repo.findAnswerById(id);
			
			assertEquals(SUBJECT,answer.getSubject());
			assertEquals(CONTENT,answer.getContent());
			assertEquals(URL,answer.getUrl());
			assertTrue(VIDEO.getNumber()==answer.getVideo().getNumber());
		}finally{
			repo.delete(id);
		}
		
	}
	
	@Test
	public void testUpdateAnswer(){
		
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
			
			repo.save(answer);
			answer = null;
			answer = repo.findAnswerById(id);
						
			assertEquals(SUBJECT_UDATED,answer.getSubject());
			assertEquals(CONTENT_UDATED,answer.getContent());
			assertEquals(URL_UDATED,answer.getUrl());
			assertTrue(VIDEO_UDATED.getNumber()==answer.getVideo().getNumber());
		}finally{
			repo.delete(id);
		}
		
	}
}
