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
	
	
	@RequestMapping("/admin/answers")
	public List<AnswerSummary> getAnswers(){
		return service.getAnswers();
	}
	
	@RequestMapping("/answers")
	public List<AnswerSummary> getListAnswers(){
		return service.getListAnswers();
	}
	
	@RequestMapping("/answers/{from}/{to}")
	public List<AnswerSummary> getListAnswersBloc(@PathVariable Integer from,@PathVariable Integer to){
		return service.getListAnswersBloc(from, to);
	}
	
	@RequestMapping("/admin/summary-answer/{id}")
	public AnswerSummary getAnswer(@PathVariable Integer id){
		return service.getAnswerSummary(id);
	}
	
	@RequestMapping("/admin/answer/{id}")
	public Answer getAnswerDetail(@PathVariable Integer id){
		return service.getAnswer(id);
	}
	
	@RequestMapping("/answer/{id}")
	public Answer getAnswerDetail2(@PathVariable Integer id){
		return service.getAnswer(id);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/admin2/answer")
	public Answer updateAnswer(@RequestBody Answer answer){
		return service.update(answer);
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/admin2/answer")
	public Answer addAnswer(@RequestBody Answer answer){
		return service.save(answer);
	}
	
	@RequestMapping("/admin/topanswers")
	public List<Answer> getTopAnswers(@RequestParam Boolean feedback){
		List<Answer> answers = service.getTopAnswers(feedback); 
		return answers;
		
	}
	
	
}
