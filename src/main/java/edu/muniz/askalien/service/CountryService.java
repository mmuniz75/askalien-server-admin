package edu.muniz.askalien.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.muniz.askalien.dao.CountryRepository;
import edu.muniz.askalien.model.Country;

@Service
public class CountryService {
	
	@Autowired
	private CountryRepository countryRepo;
	
	public List<Country> getCountryQuestions() {
		return countryRepo.getCountryQuestions();
	}
	
	
}
