package edu.muniz.askalien.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name="video",uniqueConstraints=@UniqueConstraint(columnNames="number"))
public class Video implements Model,Serializable{
	
	private static final long serialVersionUID = -821077114339966585L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	
	@Column(unique=true)
	private Integer number;
	
	private Date creationDate;

	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public void populate(Model sourceObject) {
		Video newObject = (Video)sourceObject;
		this.number = newObject.getNumber();
		this.creationDate = newObject.getCreationDate();
	}
	

	public String getDate() {
		String date = "";
		if(creationDate!=null){
			SimpleDateFormat dt1 = new SimpleDateFormat("MM/dd/yyyy");
			date = dt1.format(creationDate);
		}	
		return date;
	}
	
	public String getFormatedDate() {
		String date = "";
		if(creationDate!=null){
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
			date = dt1.format(creationDate);
		}	
		return date;
	}
}
