package edu.muniz.askalien.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.muniz.askalien.model.Video;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoRepositoryTests {
	
	@Autowired
	private VideoRepository repo;

	@Test
	public void testVideoQuestions(){
		List<Video> videos = repo.findAllByOrderByNumberDesc();
		
		Integer size = videos.size(); 
		assertTrue(size>=153);
		
		Video Video = videos.get(size-2);
		assertTrue(Video.getNumber()==1);
		
		Date creationDateTest = Date.from(LocalDateTime.of(2010,9,10,0,0,0).atZone(ZoneId.systemDefault()).toInstant());
		assertTrue(Video.getCreationDate().compareTo(creationDateTest)==0);
		
	}
	
	@Test 
	public void saveVideo(){
		final Integer NUMBER = -1;
		final Date CREATION_DATE = Date.from(LocalDateTime.of(2100,7,15,0,0,0).atZone(ZoneId.systemDefault()).toInstant());
		Video video = new Video();
		video.setCreationDate(CREATION_DATE);
		video.setNumber(NUMBER);
		
		repo.save(video);
		Integer id = video.getId();
		
		video = null;
		
		try{
			video = repo.findOne(id);
			assertEquals(video.getNumber(),NUMBER);
			assertTrue(video.getCreationDate().compareTo(CREATION_DATE)==0);
		}finally{
			repo.delete(id);
		}
		
	}
	
	@Test 
	public void updateVideo(){
		final Integer NUMBER = -1;
		final Date CREATION_DATE = Date.from(LocalDateTime.of(2100,7,15,0,0,0).atZone(ZoneId.systemDefault()).toInstant());
		final Date CREATION_DATE_UPDATED = Date.from(LocalDateTime.of(2100,7,15,0,0,0).atZone(ZoneId.systemDefault()).toInstant());
		Video video = new Video();
		video.setCreationDate(CREATION_DATE);
		video.setNumber(NUMBER);
		
		repo.save(video);
		Integer id = video.getId();
		
		video = null;
		
		try{
			video = repo.findOne(id);
			video.setCreationDate(CREATION_DATE_UPDATED);
			repo.save(video);
			video = null;
			
			video = repo.findOne(id);
			assertEquals(video.getNumber(),NUMBER);
			assertTrue(video.getCreationDate().compareTo(CREATION_DATE_UPDATED)==0);
		}finally{
			repo.delete(id);
		}
		
	}

}
