package edu.muniz.askalien.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.muniz.askalien.dao.UsageRepository;
import edu.muniz.askalien.model.Usage;


@Service
public class UsageService{
	
	@Autowired
	private UsageRepository repo;
	
	public List<Usage> getUsageFromYear(Short year){
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
	
	public void updateUsage(){
		repo.updateUsage();
	}
	
}
