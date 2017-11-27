package edu.muniz.askalien.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.muniz.askalien.dao.AnswerRepository;
import edu.muniz.askalien.dao.AnswerSummary;
import edu.muniz.askalien.dao.VideoRepository;
import edu.muniz.askalien.model.Answer;
import edu.muniz.askalien.model.Video;
import edu.muniz.askalien.util.IndexingHelper;

@Service
public class AnswerService {

	@Autowired
	private AnswerRepository repo;
	
	@Autowired
	IndexingHelper indexing;
	
	@Autowired
	private VideoService videoService;
	
	public List<AnswerSummary>getAnswers(){
		return repo.findAllSummary();
	}
		
	public AnswerSummary getAnswerSummary(Integer id){
		return repo.findById(id);
	}
	
	public Answer getAnswer(Integer id){
		return repo.findAnswerById(id);
	}
	
	public Answer save(Answer answer) {
		return saveOrUpdate(answer,true);
	}
		
	public void update(Answer answer) {
		saveOrUpdate(answer,false);
	}
	
	private Answer saveOrUpdate(Answer answer,boolean save) {
		
		Video video = answer.getVideo();
		
		if(video.getNumber()==0)
			throw new IllegalStateException("Video answer was not set");
			
		if(video.getId()==null || video.getId()==0)
			answer.setVideo(videoService.getVideofromNumber(video.getNumber()));
		
		repo.save(answer);
		
		String content = answer.getContent().replaceAll("\\<.*?>"," ");
		content = content.replaceAll("&nbsp;"," ");
		if(save)
			indexing.indexObject(answer.getId(), answer.getSubject(), content);
		else
			indexing.updateIndexing(answer.getId(), answer.getSubject(), content);
		
		return answer;
		
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
