package edu.muniz.askalien.service;

import java.util.List;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import edu.muniz.askalien.client.ElastickSearchClient;
import edu.muniz.askalien.client.SearchRequest;
import edu.muniz.askalien.dao.AnswerRepository;
import edu.muniz.askalien.dao.AnswerSummary;
import edu.muniz.askalien.model.Answer;
import edu.muniz.askalien.model.Video;

@Service
public class AnswerService {
	
	@Value("${elastick.search.enable:false}")
	private Boolean elastickEanble;

	@Autowired
	private AnswerRepository repo;
		
	@Autowired
	private ElastickSearchClient search;
	
	@Autowired
	private VideoService videoService;
	
	public List<AnswerSummary>getAnswers(){
		return repo.findAllSummary();
	}
	
	public List<AnswerSummary>getListAnswers(){
		return repo.findAllSummaryAsc();
	}
	
	public List<AnswerSummary>getListAnswersBloc(Integer from,Integer to){
		return repo.findAllSummaryBloc(from, to);
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
		
	public Answer update(Answer answer) {
		return saveOrUpdate(answer,false);
	}
	
	private Answer saveOrUpdate(Answer answer,boolean save) {
		
		Video video = answer.getVideo();
		
		if(video.getNumber()==0)
			throw new IllegalStateException("Video answer was not set");
			
		Video remoteVideo = videoService.getVideofromNumber(video.getNumber());
		
		if(remoteVideo==null)
			throw new IllegalStateException("Video " + video.getNumber() + " does not exists");
		
		answer.setVideo(remoteVideo);
		
		repo.save(answer);
		
		//if(elastickEanble)
//			search.putAnswer(SearchRequest.builder().subject(answer.getSubject()).content(Jsoup.parse(answer.getContent()).text()).build(), answer.getId());
		
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
	}
}
