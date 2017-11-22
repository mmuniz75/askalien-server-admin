package edu.muniz.askalien.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.muniz.askalien.dao.CountryRepository;
import edu.muniz.askalien.model.Country;

@Service
public class CountryService {
	
		
	private static Map<String,String> mapCountries = new HashMap<String,String>();
	static{
		for (Locale locale : Locale.getAvailableLocales()) {
		  if(locale.getCountry()!=null && !locale.getCountry().equals("")){
			mapCountries.put(locale.getDisplayCountry().toUpperCase(), locale.getCountry().toLowerCase());
		  }	
		}
		mapCountries.put("RUSSIAN FEDERATION","ru");
		mapCountries.put("NEPAL","ne");
		
	}
	
	public static String getCountryCode(String country){
		String code = mapCountries.get(country);
		if(code==null){
			System.out.println("**** COUNTRY " + country + " does not have code");
			code = country;
		}
		return code;
	}
	
	@Autowired
	private CountryRepository countryRepo;
	
	public List<Country> getCountryQuestions() {
		return countryRepo.getCountryQuestions();
	}
	
	public Map<String,Long> getCountryQuestionsByCode(){
		List<Country> countryQuestions = countryRepo.getCountryQuestions();
		Map<String,Long> countryQuestionsByCode = 
				countryQuestions.stream()
				.collect(Collectors.toMap(country -> CountryService.getCountryCode(country.getCountry()), Country::getCountQuestions));
		

		return countryQuestionsByCode;
	}
	
	
}
