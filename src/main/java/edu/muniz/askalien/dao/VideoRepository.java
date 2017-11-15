package edu.muniz.askalien.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import edu.muniz.askalien.model.Video;

public interface VideoRepository extends CrudRepository<Video, Integer>{
	
	Video findByNumber(Integer number);
	List<Video> findAllByOrderByNumberDesc();

}
