package com.ibm.rms.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
@XmlRootElement
public class Job {
	private String id;
	private String title;
	private String descricption;
	private String ctc;
	private String experience;
	
	public Job(String id, String title, String descricption, String ctc, String experience) {
		super();
		this.id = id;
		this.title = title;
		this.descricption = descricption;
		this.ctc = ctc;
		this.experience = experience;
	}
	
	public Job() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescricption() {
		return descricption;
	}
	public void setDescricption(String descricption) {
		this.descricption = descricption;
	}
	public String getCtc() {
		return ctc;
	}
	public void setCtc(String ctc) {
		this.ctc = ctc;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	
	
}
