package edu.muniz.askalien.service;

public class StatisticDTO {
	
	private Long totalQuestion;
	private Integer totalFrequentUsers;
	private Long totalUsers;
	private Long totalCountries;
	private Long totalAnswers;
	
	public StatisticDTO(Long totalQuestion, Integer totalFrequentUsers, Long totalUsers, Long totalCountries,
			Long totalAnswers) {
		super();
		this.totalQuestion = totalQuestion;
		this.totalFrequentUsers = totalFrequentUsers;
		this.totalUsers = totalUsers;
		this.totalCountries = totalCountries;
		this.totalAnswers = totalAnswers;
	}
	public Long getTotalQuestion() {
		return totalQuestion;
	}
	public Integer getTotalFrequentUsers() {
		return totalFrequentUsers;
	}
	public Long getTotalUsers() {
		return totalUsers;
	}
	public Long getTotalCountries() {
		return totalCountries;
	}
	public Long getTotalAnswers() {
		return totalAnswers;
	}

}
