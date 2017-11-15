package edu.muniz.askalien.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.muniz.askalien.dao.VideoRepository;
import edu.muniz.askalien.model.Video;

@Service
public class VideoService {
	
	@Autowired
	private VideoRepository videoRepo;
	
	public Video getVideofromNumber(Integer number){
		return videoRepo.findByNumber(number);
	}
		
	public List<Video> getList() {
		return videoRepo.findAllByOrderByNumberDesc();
	}


	public void save(Video video) {
		videoRepo.save(video);
	}
	

}
