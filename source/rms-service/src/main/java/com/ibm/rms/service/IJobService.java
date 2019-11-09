package com.ibm.rms.service;

import java.util.ArrayList;
import java.util.List;

import com.ibm.rms.exception.RmsApplicationException;
import com.ibm.rms.model.Job;

public interface IJobService {

	public boolean jobCreate(Job job) throws RmsApplicationException;
	
	public List<Job> getAll() throws RmsApplicationException;
	
	public Job getById(String id) throws RmsApplicationException;
	
	public boolean updateJob(Job updatedJob) throws RmsApplicationException;
	
	public boolean deleteJob(String id) throws RmsApplicationException;
	
	public ArrayList<Job> filterBySkillAndExperience(String skill, String experience);
	
	public ArrayList<Job> filterByExperience(String experience);
	
	public void setInterviewDate(Job job) throws RmsApplicationException;

	ArrayList<Job> filterBySkill(String skill);
	
}
