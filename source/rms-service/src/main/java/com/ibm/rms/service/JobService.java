package com.ibm.rms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ibm.rms.exception.RmsApplicationException;
import com.ibm.rms.model.Candidate;
import com.ibm.rms.model.EmailMessage;
import com.ibm.rms.model.Job;
import com.ibm.rms.repository.JobRepository;
import com.mongodb.DB;
//import com.mongodb.client.MongoClient;
import com.mongodb.MongoClient;

@Service
public class JobService {
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	JobRepository jobRepo;
	SequenceGeneratorService sequenceGeneratorService;
	
	 MongoClient mongoClient = new MongoClient();
	 DB db = mongoClient.getDB("test");
	
	public boolean jobCreate(Job job) throws RmsApplicationException {
		try {
			job.setjId(sequenceGeneratorService.generateSequence(db,Job.SEQUENCE_NAME));
			jobRepo.save(job);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new RmsApplicationException("Can not create job now; try again after sometime",e);
		}
	}

	public List<Job> getAll() {
		return jobRepo.findAll();
	}

	public Job getById(String id) {
		return jobRepo.findById(id).get();
	}

	public boolean updateJob(Job updatedJob) throws RmsApplicationException {
		try {
			jobRepo.save(updatedJob);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new RmsApplicationException("Job can not be updated now; try again after sometime",e);
		}
	}

	public boolean deleteJob(String id) throws RmsApplicationException {
		try {
			jobRepo.deleteById(id);
			return true;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new RmsApplicationException("Job can not be deleted now; try again after sometime",e);
		}
	}

	public ArrayList<Job> filterBySkill(String skill) {
		ArrayList<Job> filteredJobs = new ArrayList<Job>();
		ArrayList<Job> allJobs = (ArrayList<Job>) jobRepo.findAll();
		allJobs.forEach(a -> {
			if(a.getSkillList().contains(skill)) {
				filteredJobs.add(a);
			}
		});
		return filteredJobs;
	}
	
	public ArrayList<Job> filterBySkillAndExperience(String skill, String experience) {
		ArrayList<Job> filteredJobs = new ArrayList<Job>();
		ArrayList<Job> allJobs = (ArrayList<Job>) jobRepo.findAll();
		allJobs.forEach(a -> {
			if(a.getSkillList().contains(skill) && a.getjRequiredExperience().equals(experience)) {
				filteredJobs.add(a);
			}
		});
		return filteredJobs;
	}
	
	public ArrayList<Job> filterByExperience(String experience) {
		ArrayList<Job> filteredJobs = new ArrayList<Job>();
		ArrayList<Job> allJobs = (ArrayList<Job>) jobRepo.findAll();
		allJobs.forEach(a -> {
			if(a.getjRequiredExperience().equals(experience)) {
				filteredJobs.add(a);
			}
		});
		return filteredJobs;
	}

	public void setInterviewDate(Job job) throws RmsApplicationException {
		try {
			jobRepo.save(job);
			Date interviewDate = job.getjInterviewDate();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String interviewDateString = format.format(interviewDate);
			ArrayList<Candidate> appliedCandidate = new ArrayList<Candidate>();
			appliedCandidate = job.getjAppliedCandidateList();
			appliedCandidate.forEach( c -> {
				
				EmailMessage emailMessage = new EmailMessage();
				emailMessage.setTo_address(c.getcEmail());
				emailMessage.setSubject("Your job application update");
				emailMessage.setBody("Dear " +c.getcName()+","+ "\r\n" + 
						"The Interview for the " +job.getjTitle()+"from IBM ; That you have applied for is scheduled on "+interviewDateString+" .");
				try {
					emailService.sendmail(emailMessage);
				} catch (RmsApplicationException e) {
					e.printStackTrace();
				}
				
			});
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new RmsApplicationException("Job can not be updated now; try again after sometime",e);
		}
	}
}
