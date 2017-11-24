package edu.muniz.askalien.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.muniz.askalien.dao.QuestionFilter;
import edu.muniz.askalien.dao.QuestionRepository;
import edu.muniz.askalien.model.Question;

@Service
public class QuestionService{
	
	
	@Autowired
	private QuestionRepository questionRepo;
	
	public List<Question> getQuestions(QuestionFilter filter){
		return questionRepo.findAll(filter);
	}
	
	public Question getQuestion(Integer id){
		return questionRepo.findQuestionById(id);
	}
	
	public Long getCountQuestions() {
		return questionRepo.count();
	}
	
	public Integer getFrequentUsers() {
		return questionRepo.findFrequentUsers().size();
	}
	
	public Long getCountUsers() {
		return questionRepo.findCountUsers();
		
	}
	
	public Long getCountCountries() {
		return questionRepo.findCountCountries(); 
	}	
	
	public List<Question> getQuestionsByAnwerId(Integer id){
		return questionRepo.findByAnswerId(id);
	}
	
}
