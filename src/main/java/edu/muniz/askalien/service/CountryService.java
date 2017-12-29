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
		mapCountries.put("BARBADOS","bb");
		mapCountries.put("GEORGIA","ka");
		mapCountries.put("TRINIDAD AND TOBAGO","tt");
		mapCountries.put("KAZAKHSTAN","kk");
		mapCountries.put("CAPE VERDE","cv");
		mapCountries.put("MADAGASCAR","mg");
		mapCountries.put("GUAM","gu");
		mapCountries.put("NIGERIA","ng");
		mapCountries.put("GUYANA","gy");
		mapCountries.put("SYRIAN ARAB REPUBLIC","sy");
		mapCountries.put("KYRGYZSTAN","kg");
		mapCountries.put("BOTSWANA","bw");
		mapCountries.put("AZERBAIJAN","az");
		mapCountries.put("SURINAME","sr");
		mapCountries.put("ARMENIA","hy");
		mapCountries.put("BELIZE","bz");
		mapCountries.put("BANGLADESH","bd");
		mapCountries.put("TANZANIA","tz");
		mapCountries.put("MONGOLIA","mn");
		mapCountries.put("IRAN","ir");
		mapCountries.put("ZIMBABWE","zw");
		mapCountries.put("CAMBODIA","kh");
		mapCountries.put("SRI LANKA","lk");
		mapCountries.put("MOZAMBIQUE","mz");
		mapCountries.put("GEORGIA","ge");
		mapCountries.put("KAZAKHSTAN","kz");
		mapCountries.put("NEPAL","np");
		mapCountries.put("ARMENIA","am");
		
	}
	
	public static String getCountryCode(String country){
		String code = mapCountries.get(country);
		if(code==null){
			System.out.println("mapCountries.put(\""+ country +"\",\"\");");
			code = country;
		}
		return code;
	}
	
	@Autowired
	private CountryRepository countryRepo;
	
	public List<Country> getCountryQuestions() {
		return countryRepo.getCountryQuestions();
	}
	
	public Map<String,String> getCountryQuestionsByCode(){
		List<Country> countryQuestions = countryRepo.getCountryQuestions();
		Map<String,String> countryQuestionsByCode = 
				countryQuestions.stream()
				.collect(Collectors.toMap(country -> CountryService.getCountryCode(country.getCountry()), country -> country.getCountQuestions().toString()));
		

		return countryQuestionsByCode;
	}
	
	
}
