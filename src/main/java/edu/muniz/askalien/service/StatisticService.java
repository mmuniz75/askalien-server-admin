package edu.muniz.askalien.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
	
	@Autowired
	private AnswerService answerService;
	
	@Autowired
	private QuestionService questionService;
	
	
	public StatisticDTO getAccessStatistic(){
		
		Long totalQuestion = questionService.getCountQuestions(); 
		Integer totalFrequentUsers = questionService.getFrequentUsers(); 
		Long totalUsers = questionService.getCountUsers();
		Long totalCountries = questionService.getCountCountries();
		Long totalAnswers = answerService.getCountAnswers();
		
		StatisticDTO dto = new StatisticDTO(totalQuestion, totalFrequentUsers, totalUsers, totalCountries, totalAnswers); 
		
		return dto;
	}


}
