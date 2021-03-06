package edu.muniz.askalien.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.muniz.askalien.model.Usage;
import edu.muniz.askalien.model.View;
import edu.muniz.askalien.service.StatisticDTO;
import edu.muniz.askalien.service.StatisticService;
import edu.muniz.askalien.service.UsageService;
import edu.muniz.askalien.service.ViewService;

@RestController
public class StatisticRest {

	@Autowired
	StatisticService service;
	
	@Autowired
	UsageService usageService;
	
	@Autowired
	ViewService viewService;
		
	@RequestMapping("/admin/statistics")
	public StatisticDTO getStatistics(){
		return service.getAccessStatistic();
	}
	
	@RequestMapping("/admin/usage/{year}")
	public List<Usage> getUsage(@PathVariable Short year){
		return usageService.getUsageFromYear(year);
	}
	
	@RequestMapping("/admin/view/{year}")
	public List<View> getView(@PathVariable Short year){
		return viewService.getViewFromYear(year);
	}
	
}
