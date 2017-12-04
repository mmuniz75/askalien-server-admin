package edu.muniz.askalien.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.muniz.askalien.model.Video;
import edu.muniz.askalien.service.VideoService;

@RestController
public class VideoRest {

	@Autowired
	VideoService service;
		
	@RequestMapping("/admin/videos")
	public List<Video> getVideos(){
		return service.getList();
	}
		
	@RequestMapping("/admin/video/{id}")
	public Video getAnswerDetail(@PathVariable Integer id){
		return service.getVideofromNumber(id);
	}
		
	@RequestMapping(method=RequestMethod.POST,value="/admin/video")
	public Video addAnswer(@RequestBody Video video){
		return service.save(video);
	}
	
}
