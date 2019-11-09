package com.ibm.rms.model;

import java.util.ArrayList;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("organisation")
public class Organisation {
	
	private String oId;
	private String oName;
	private ArrayList<Job> oJobList;
	private ArrayList<Hr> oHrList;
	
	
	public Organisation() {
		
	}
	
	
	public Organisation(String oId, String oName, ArrayList<Job> oJobList, ArrayList<Hr> oHrList) {
		super();
		this.oId = oId;
		this.oName = oName;
		this.oJobList = oJobList;
		this.oHrList = oHrList;
	}

	
	public String getoId() {
		return oId;
	}

	public void setoId(String oId) {
		this.oId = oId;
	}

	public String getoName() {
		return oName;
	}

	public void setoName(String oName) {
		this.oName = oName;
	}

	public ArrayList<Job> getoJobList() {
		return oJobList;
	}

	public void setoJobList(ArrayList<Job> oJobList) {
		this.oJobList = oJobList;
	}

	public ArrayList<Hr> getoHrList() {
		return oHrList;
	}

	public void setoHrs(ArrayList<Hr> oHrList) {
		this.oHrList = oHrList;
	}

	@Override
	public String toString() {
		return "Organisation [oId=" + oId + ", oName=" + oName + ", oJobList=" + oJobList + ", oHrs=" + oHrList + "]";
	}
	
}
