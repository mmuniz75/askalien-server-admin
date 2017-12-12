package edu.muniz.askalien.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import edu.muniz.askalien.dao.AnswerSummary;
import edu.muniz.askalien.model.Answer;
import edu.muniz.askalien.service.AnswerService;

@RestController
public class AnswerController {
	
	@Autowired
	AnswerService service;
	
	@RequestMapping("/list")
	public ModelAndView list() {
		return new ModelAndView("list","answers",service.getListAnswers());
	}
	
	@RequestMapping("/list/{from}/{to}")
	public ModelAndView listAnswersBloc(@PathVariable Integer from,@PathVariable Integer to){
		return new ModelAndView("list","answers",service.getListAnswersBloc(from, to));
	}
	
	@RequestMapping("/view/{id}")
	public ModelAndView view(@PathVariable("id") Integer id) {
		return new ModelAndView("view","answer",service.getAnswer(id));
	}
	

}
