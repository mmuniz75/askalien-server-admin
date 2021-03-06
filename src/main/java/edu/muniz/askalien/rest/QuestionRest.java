package edu.muniz.askalien.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.muniz.askalien.dao.QuestionFilter;
import edu.muniz.askalien.model.Question;
import edu.muniz.askalien.service.QuestionService;

@RestController
public class QuestionRest {
	
	@Autowired
	QuestionService service;
	
	@RequestMapping(method=RequestMethod.POST,value="/admin/questions")
	public List<Question> getQuestions(@RequestBody QuestionFilter filter){
		return service.getQuestions(filter);
	}
	
	@RequestMapping("/admin/questions")
	public List<Question> getQuestions(){
		QuestionFilter filter = new QuestionFilter();
		filter.setJustThisMonth(true);
		List<Question> questions = service.getQuestions(filter); 
		return questions;
		
	}
	
	@RequestMapping("/admin/question/{id}")
	public Question getQuestion(@PathVariable Integer id){
		return service.getQuestion(id);
		
	}
		
	@RequestMapping("/admin/questions/count")
	public Long getCountQuestions(){
		return service.getCountQuestions();
	}
	
	@RequestMapping("/admin/questions/{id}")
	public List<Question> getQuestionByAnswerId(@PathVariable Integer id){
		return service.getQuestionsByAnwerId(id);
		
	}

}
