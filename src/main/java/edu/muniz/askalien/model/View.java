package edu.muniz.askalien.model;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.util.Locale;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="view_question")
public class View implements Model,Serializable{
	
	private static final long serialVersionUID = -6431402678676165430L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private Short year;
	private Byte month;
	private Long number;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Short getYear() {
		return year;
	}

	public void setYear(Short year) {
		this.year = year;
	}

	public Byte getMonth() {
		return month;
	}



	public void setMonth(Byte month) {
		this.month = month;
	}


	public void populate(Model sourceObject) {
		View usage = (View)sourceObject;
		usage.setMonth(usage.getMonth());
		usage.setYear(usage.getYear());
		usage.setNumber(usage.getNumber());
	} 
	
	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getMonthName(){
		String[] months = (new DateFormatSymbols(Locale.US)).getMonths();
		return months[month-1]; 
	}
		
	
}
