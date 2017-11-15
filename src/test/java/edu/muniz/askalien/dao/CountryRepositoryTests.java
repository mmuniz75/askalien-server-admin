package edu.muniz.askalien.dao;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import edu.muniz.askalien.model.Country;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CountryRepositoryTests {
	
	@Autowired
	private CountryRepository repo;

	@Test
	public void testCountryQuestions(){
		List<Country> countries = repo.getCountryQuestions();
		
		assertTrue(countries.size()>=108);
		
		Country country = countries.get(0);
		assertEquals(country.getCountry(),"UNITED STATES");
		assertTrue(country.getCountQuestions()>30000);
		
	}
	
	@Test 
	public void saveCountry(){
		final String COUNTRY = "Country";
		final String IP = "1.2.3.4.5";
		Country country = new Country(IP,COUNTRY);
		repo.save(country);
		Integer id = country.getId();
		
		country = null;
		
		try{
			country = repo.findOne(id);
			assertEquals(country.getIp(),IP);
			assertEquals(country.getCountry(),COUNTRY);
		}finally{
			repo.delete(id);
		}
		
	}
	

}
