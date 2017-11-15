package edu.muniz.askalien.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.muniz.askalien.dao.AnswerRepository;
import edu.muniz.askalien.dao.AnswerSummary;
import edu.muniz.askalien.model.Answer;
import edu.muniz.askalien.util.IndexingHelper;

@Service
public class AnswerService {

	@Autowired
	private AnswerRepository repo;
	
	@Autowired
	IndexingHelper indexing;
	
	public List<AnswerSummary>getAnswers(){
		return repo.findAllSummary();
	}
		
	public AnswerSummary getAnswerSummary(Integer id){
		return repo.findById(id);
	}
	
	public Answer getAnswer(Integer id){
		return repo.findAnswerById(id);
	}
	
	public void save(Answer answer) {
		saveOrUpdate(answer,true);
	}
		
	public void update(Answer answer) {
		saveOrUpdate(answer,false);
	}
	
	private void saveOrUpdate(Answer answer,boolean save) {
		repo.save(answer);
		
		String content = answer.getContent().replaceAll("\\<.*?>"," ");
		content = content.replaceAll("&nbsp;"," ");
		if(save)
			indexing.indexObject(answer.getId(), answer.getSubject(), content);
		else
			indexing.updateIndexing(answer.getId(), answer.getSubject(), content);					
		
	}
	
	
	public List<Answer>getTopAnswers(Boolean feedBack){
		List<Answer> topAnswers = feedBack?repo.findTopAnswersJustFeedBack():repo.findTopAnswers(); 
		return topAnswers;
	}
	
	public Long getCountAnswers() {
		return repo.count();
	}
	
	public void removeAnswer(Integer id){
		repo.delete(id);
		indexing.removeObject(id);
	}
}
