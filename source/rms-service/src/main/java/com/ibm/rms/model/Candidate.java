package com.ibm.rms.model;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("candidates")
public class Candidate {

	@Transient
    public static final String SEQUENCE_NAME = "candidates_sequence";
	
	@Id
	private String cId;
	private String cName;
	private String username;
	private String cEmail;
	private String cPhone;
	private Address cAddress;
	private ArrayList<String> skillList;
	private Resume cResume;
	private String cExperience;
	private ArrayList<Job> cAppliedJobList = new ArrayList();
	
	public Candidate() {
		super();
	}

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getcEmail() {
		return cEmail;
	}

	public void setcEmail(String cEmail) {
		this.cEmail = cEmail;
	}

	public String getcPhone() {
		return cPhone;
	}

	public void setcPhone(String cPhone) {
		this.cPhone = cPhone;
	}

	public Address getcAddress() {
		return cAddress;
	}

	public void setcAddress(Address cAddress) {
		this.cAddress = cAddress;
	}

	public ArrayList<String> getSkillList() {
		return skillList;
	}

	public void setSkillList(ArrayList<String> skillList) {
		this.skillList = skillList;
	}

	public Resume getcResume() {
		return cResume;
	}

	public void setcResume(Resume cResume) {
		this.cResume = cResume;
	}

	public String getcExperience() {
		return cExperience;
	}

	public void setcExperience(String cExperience) {
		this.cExperience = cExperience;
	}

	public ArrayList<Job> getcAppliedJobList() {
		return cAppliedJobList;
	}

	public void setcAppliedJobList(ArrayList<Job> cAppliedJobList) {
		this.cAppliedJobList = cAppliedJobList;
	}


	@Override
	public String toString() {
		return "Candidate [cId=" + cId + ", cName=" + cName + ", cEmail=" + cEmail + ", cPhone=" + cPhone
				+ ", cAddress=" + cAddress + ", skillList=" + skillList + ", cResume=" + cResume + ", cExperience="
				+ cExperience + ", cAppliedJobList=" + cAppliedJobList + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		Candidate c = (Candidate)obj;
		if(c.getcId().equals(this.getcId())) {
			return true;
		}
		return false;
	}
}
