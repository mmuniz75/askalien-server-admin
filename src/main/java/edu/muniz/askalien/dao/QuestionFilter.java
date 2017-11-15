package edu.muniz.askalien.dao;

import java.util.Date;

public class QuestionFilter {
	
		
	private boolean justFeedback;
	private boolean justThisMonth;
	private Date startDate;
	private Date endDate;
	private Integer answerId;
	
	
	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
		this.justThisMonth = false;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
		this.justThisMonth = false;
	}

	public boolean isJustThisMonth() {
		return justThisMonth;
	}

	public void setJustThisMonth(boolean justThisMonth) {
		this.justThisMonth = justThisMonth;
		this.endDate = null;
		this.startDate = null;
	}

	private String question;
	private String ipFilter;
	
	
	public String getIpFilter() {
		return ipFilter;
	}

	public void setIpFilter(String ipFilter) {
		this.ipFilter = ipFilter;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public boolean isJustFeedback() {
		return justFeedback;
	}

	public void setJustFeedback(boolean justFeedback) {
		this.justFeedback = justFeedback;
	}

}
