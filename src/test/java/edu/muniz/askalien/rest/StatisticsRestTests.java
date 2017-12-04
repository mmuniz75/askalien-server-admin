package edu.muniz.askalien.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.hibernate.stat.Statistics;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.muniz.askalien.model.Video;
import edu.muniz.askalien.service.StatisticDTO;
import edu.muniz.askalien.service.StatisticService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StatisticsRestTests {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void getStatistics() throws Exception{
		
		String URL="/admin/statistics";
			
		this.mvc.perform(get(URL))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("totalQuestion", greaterThan(90000)))
			.andExpect(jsonPath("totalFrequentUsers", greaterThan(1400)))
			.andExpect(jsonPath("totalUsers", greaterThan(4800)))
			.andExpect(jsonPath("totalAnswers", greaterThan(1200)))
			.andExpect(jsonPath("totalCountries", greaterThan(100)))
		;
		
	}

	@Test
	public void getUsage() throws Exception{
		
		String URL="/admin/usage/2016";
				
		this.mvc.perform(get(URL))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(12)))
			.andExpect(jsonPath("$[0].year", is(2016)))
			.andExpect(jsonPath("$[0].month", is(1)))
			.andExpect(jsonPath("$[0].numberUsers", is(69)))
			.andExpect(jsonPath("$[0].newUsers", is(49)))
			.andExpect(jsonPath("$[0].monthName", is("January")))
			.andExpect(jsonPath("$[0].oldUsers", is(20)))
			.andExpect(jsonPath("$[11].year", is(2016)))
			.andExpect(jsonPath("$[11].month", is(12)))
			.andExpect(jsonPath("$[11].numberUsers", is(72)))
			.andExpect(jsonPath("$[11].newUsers", is(60)))
			.andExpect(jsonPath("$[11].monthName", is("December")))
			.andExpect(jsonPath("$[11].oldUsers", is(12)))
		;
		
	}
	
	
}
