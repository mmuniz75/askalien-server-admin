package edu.muniz.askalien.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import edu.muniz.askalien.model.Country;

public interface CountryRepository extends CrudRepository<Country, Integer>{
	
	@Query("SELECT new edu.muniz.askalien.model.Country(question.country,count(question.country)) "
			+ "FROM Question question "
			+ "WHERE question.country "
			+ "NOT IN ('','undefined','Unknown Country') "
			+ "GROUP BY question.country "
			+ "ORDER BY 2 desc")	
	List<Country> getCountryQuestions();

}
