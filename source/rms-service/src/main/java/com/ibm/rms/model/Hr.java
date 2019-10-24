package com.ibm.rms.model;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("hr")
public class Hr extends Organisation{
	
	private String hId;
	private String hName;
	private String hEmail;
	private String hPhone;
	private ArrayList<Job> hPostedJobs;
	private Organisation hOraganisation;
	
	//Constructor without parameter
	public Hr() {
		super();
	}
	
	//Constructor with parameters
	public Hr(String hId, String hName, String hEmail, String hPhone, ArrayList<Job> hPostedJobs,
			Organisation hOraganisation) {
		super();
		this.hId = hId;
		this.hName = hName;
		this.hEmail = hEmail;
		this.hPhone = hPhone;
		this.hPostedJobs = hPostedJobs;
		this.hOraganisation = hOraganisation;
	}
	
	//Getters... and Setters...
	public String gethId() {
		return hId;
	}
	public void sethId(String hId) {
		this.hId = hId;
	}
	public String gethName() {
		return hName;
	}
	public void sethName(String hName) {
		this.hName = hName;
	}
	public String gethEmail() {
		return hEmail;
	}
	public void sethEmail(String hEmail) {
		this.hEmail = hEmail;
	}
	public String gethPhone() {
		return hPhone;
	}
	public void sethPhone(String hPhone) {
		this.hPhone = hPhone;
	}
	public ArrayList<Job> gethPostedJobs() {
		return hPostedJobs;
	}
	public void sethPostedJobs(ArrayList<Job> hPostedJobs) {
		this.hPostedJobs = hPostedJobs;
	}
	public Organisation gethOraganisation() {
		return hOraganisation;
	}
	public void sethOraganisation(Organisation hOraganisation) {
		this.hOraganisation = hOraganisation;
	}
	
	@Override
	public String toString() {
		return "Hr [hId=" + hId + ", hName=" + hName + ", hEmail=" + hEmail + ", hPhone=" + hPhone + ", hPostedJobs="
				+ hPostedJobs + ", hOraganisation=" + hOraganisation + "]";
	}
	
}
