package com.ibm.rms.model;

import java.util.ArrayList;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("jobs")
public class Job extends Organisation{
	@Transient
    public static final String SEQUENCE_NAME = "jobs_sequence";
	
	@Id
	private String jId;
	private String jTitle;
	private String jDescricption;
	private ArrayList<String> skillList;
	private String jSalary;
	private String jRequiredExperience;
	private Date jPublishDate = new Date();
	private Date jApplicationCloseDate;
	private ArrayList<Candidate> jAppliedCandidateList = new ArrayList();
	private Organisation jOrganisation;
	
	
//	public Job(String jId, String jTitle, String jDescricption, ArrayList<String> skillList, String jSalary,
//			String jRequiredExperience, Date jApplicationCloseDate,Organisation jOrganisation) {
//		super();
//		this.jId = jId;
//		this.jTitle = jTitle;
//		this.jDescricption = jDescricption;
//		this.skillList = skillList;
//		this.jSalary = jSalary;
//		this.jRequiredExperience = jRequiredExperience;
//		this.jApplicationCloseDate = jApplicationCloseDate;
//		this.jOrganisation = jOrganisation;
//	}


	public String getjId() {
		return jId;
	}


	public void setjId(String jId) {
		this.jId = jId;
	}


	public String getjTitle() {
		return jTitle;
	}


	public void setjTitle(String jTitle) {
		this.jTitle = jTitle;
	}


	public String getjDescricption() {
		return jDescricption;
	}


	public void setjDescricption(String jDescricption) {
		this.jDescricption = jDescricption;
	}


	public ArrayList<String> getSkillList() {
		return skillList;
	}


	public void setSkillList(ArrayList<String> skillList) {
		this.skillList = skillList;
	}


	public String getjSalary() {
		return jSalary;
	}


	public void setjSalary(String jSalary) {
		this.jSalary = jSalary;
	}


	public String getjRequiredExperience() {
		return jRequiredExperience;
	}


	public void setjRequiredExperience(String jRequiredExperience) {
		this.jRequiredExperience = jRequiredExperience;
	}


	public Date getjApplicationCloseDate() {
		return jApplicationCloseDate;
	}


	public void setjApplicationCloseDate(Date jApplicationCloseDate) {
		this.jApplicationCloseDate = jApplicationCloseDate;
	}


	public ArrayList<Candidate> getjAppliedCandidateList() {
		return jAppliedCandidateList;
	}


	public void setjAppliedCandidateList(ArrayList<Candidate> jAppliedCandidateList) {
		this.jAppliedCandidateList = jAppliedCandidateList;
	}


	public Organisation getjOrganisation() {
		return jOrganisation;
	}


	public void setjOrganisation(Organisation jOrganisation) {
		this.jOrganisation = jOrganisation;
	}


	public Date getjPublishDate() {
		return jPublishDate;
	}


	@Override
	public String toString() {
		return "Job [jId=" + jId + ", jTitle=" + jTitle + ", jDescricption=" + jDescricption + ", skills=" + skillList
				+ ", jSalary=" + jSalary + ", jRequiredExperience=" + jRequiredExperience + ", jPublishDate="
				+ jPublishDate + ", jApplicationCloseDate=" + jApplicationCloseDate + ", jAppliedCandidateList="
				+ jAppliedCandidateList + ", jOrganisation=" + jOrganisation + "]";
	}
	
	
}
