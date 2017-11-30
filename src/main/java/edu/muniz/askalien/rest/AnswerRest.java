package edu.muniz.askalien.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.muniz.askalien.dao.AnswerSummary;
import edu.muniz.askalien.model.Answer;
import edu.muniz.askalien.service.AnswerService;


@RestController
public class AnswerRest {
	
	@Autowired
	AnswerService service;
	
	
	@RequestMapping("/answers")
	public List<AnswerSummary> getAnswers(){
		return service.getAnswers();
	}
	
	@RequestMapping("/summary-answer/{id}")
	public AnswerSummary getAnswer(@PathVariable Integer id){
		return service.getAnswerSummary(id);
	}
	
	@RequestMapping("/answer/{id}")
	public Answer getAnswerDetail(@PathVariable Integer id){
		return service.getAnswer(id);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/answer")
	public Answer updateAnswer(@RequestBody Answer answer){
		return service.update(answer);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/answer")
	public Answer addAnswer(@RequestBody Answer answer){
		return service.save(answer);
	}
	
	@RequestMapping("/topanswers")
	public List<Answer> getTopAnswers(@RequestParam Boolean feedback){
		List<Answer> answers = service.getTopAnswers(feedback); 
		return answers;
		
	}
	
	
}
