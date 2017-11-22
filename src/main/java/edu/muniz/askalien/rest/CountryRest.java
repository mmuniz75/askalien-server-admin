package edu.muniz.askalien.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.muniz.askalien.model.Country;
import edu.muniz.askalien.service.CountryService;

@RestController
public class CountryRest {

	@Autowired
	CountryService service;
		
	@RequestMapping("/countries")
	public List<Country> getCountries(){
		return service.getCountryQuestions();
	}
	
	@RequestMapping("/countriesCode")
	public Map<String,Long> getCountryQuestionsByCode(){
		return service.getCountryQuestionsByCode();
	}
}
