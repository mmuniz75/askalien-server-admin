package edu.muniz.askalien.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.muniz.askalien.dao.ViewRepository;
import edu.muniz.askalien.model.View;


@Service
public class ViewService{
	
	@Autowired
	private ViewRepository repo;
	
	public List<View> getViewFromYear(Short year){
		repo.updateView();
		return repo.findByYearOrderByMonthAsc(year);
	}
	
	public List<Short> getYears(){
		List<Short> years = new ArrayList<Short>();
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		for(short i=2012;i<=currentYear;i++){
			years.add(i);
		}
		
		return years;
	}
	
	public void updateView(){
		repo.updateView();
	}
	
}
