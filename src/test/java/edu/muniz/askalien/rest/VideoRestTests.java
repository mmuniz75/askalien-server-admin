package edu.muniz.askalien.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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

import edu.muniz.askalien.dao.VideoRepository;
import edu.muniz.askalien.model.Video;
import edu.muniz.askalien.service.VideoService;
import edu.muniz.askalien.util.Util;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VideoRestTests {

	@Autowired
	VideoService service;
	
	@Autowired
	VideoRepository repo;
		
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void getVideos() throws Exception{
		
		String URL="/videos";
		
		List<Video> videos = service.getList();
		int count = videos.size();
		int last = count - 1;
		
		this.mvc.perform(get(URL))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(count)))
			.andExpect(jsonPath("$[0].id", is(videos.get(0).getId())))
			.andExpect(jsonPath("$[0].formatedCreationDate", is(videos.get(0).getFormatedCreationDate())))
			.andExpect(jsonPath("$[" + last + "].id", is(videos.get(last).getId())))
			.andExpect(jsonPath("$[" + last + "].formatedCreationDate", is(videos.get(last).getFormatedCreationDate())))
			
		;
		
	}
	
	@Test
	public void getVideo() throws Exception{
		String URL="/video/100";
				
		this.mvc.perform(get(URL))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("number", is(100)))
			.andExpect(jsonPath("date", is("04/22/2013")))
		;
	}
	
	@Test 
	public void saveVideo() throws Exception{
		final Integer NUMBER = -1;
		final Date CREATION_DATE = Date.from(LocalDateTime.of(2100,7,15,0,0,0).atZone(ZoneId.systemDefault()).toInstant());
		Video video = new Video();
		video.setCreationDate(CREATION_DATE);
		video.setNumber(NUMBER);
		
		Integer id = null;
		try{
		
			String URL="/video";
			String requestJson = Util.getJson(video);
			
			this.mvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON)
					                   .content(requestJson))	
									    .andExpect(status().isOk())
			;			
			
			video = null;
			video = service.getVideofromNumber(NUMBER);
			id = video.getId();
			assertEquals(video.getNumber(),NUMBER);
			assertTrue(video.getCreationDate().compareTo(CREATION_DATE)==0);
		}finally{
			if(id!=null)
				repo.delete(id);
		}
		
	}
	
}
