package edu.muniz.askalien.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.muniz.askalien.dao.CountryRepository;
import edu.muniz.askalien.model.Country;
import edu.muniz.askalien.service.CountryService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryRestTests {
	
	@Autowired
	CountryService service;
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;
	
	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}
	
	@Test
	public void getCountries() throws Exception{
		
		String URL="/countries";
		
		List<Country> countries = service.getCountryQuestions();
		int count = countries.size();
		int last = count - 1;
		
		this.mvc.perform(get(URL))	
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(count)))
			.andExpect(jsonPath("$[0].country", is(countries.get(0).getCountry())))
			.andExpect(jsonPath("$[0].countQuestions", is(countries.get(0).getCountQuestions().intValue())))
			.andExpect(jsonPath("$[" + last + "].country", is(countries.get(last).getCountry())))
			.andExpect(jsonPath("$[" + last + "].countQuestions", is(countries.get(last).getCountQuestions().intValue())))
		;
		
	}

}
