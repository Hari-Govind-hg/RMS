package com.ibm.rms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ibm.rms.exception.RmsApplicationException;
import com.ibm.rms.model.Candidate;
import com.ibm.rms.model.EmailMessage;
import com.ibm.rms.model.Job;
import com.ibm.rms.model.SmsRequest;
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
	 DB db = mongoClient.getDB("rms");
	
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

	public List<Job> getAll() throws RmsApplicationException {
		try {
			return jobRepo.findAll();
		} catch (DataAccessException e) {
			throw new RmsApplicationException("Can not find any job now", e);
		}
	}

	public Job getById(String id) throws RmsApplicationException {
		try {
			return jobRepo.findById(id).get();
		} catch (DataAccessException e) {
			throw new RmsApplicationException("Job does not exist", e);
		}
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
				String resbody = "Dear "+c.getcName()+","+ "\n" + "The job " +job.getjTitle()+" position hiring is now active."+"\n"+"Your interview date has been set for "+interviewDateString+". Make sure you are present for the interview on the date assigned to you. Do not be late."+"\n"+"Job Title: "+job.getjTitle()+"\n"+"Job Description: "+job.getjDescription()+"\n"+"Min Experience Required: "+job.getjRequiredExperience()+"\n\n"+"Regards,"+"\n"+"RMS Team"+"\n\n\n\n"+"THIS IS A SYSTEM GENERATED MAIL.PLEASE DO NOT REPLY TO THIS MAIL.THANK YOU."+"\nFor any queries,please feel free to reach us from the Contact Us page of our website";
				emailMessage.setBody(resbody);
				try {
					emailService.sendmail(emailMessage);
					String msg = "Dear "+c.getcName()+"\n"+" Your interview for "+job.getjTitle()+"is scheduled on "+interviewDateString+"\n\n"+"Regards,"+"\n"+"RMS Team"+"\n\n"+"This is a system generated response.Please do NOT reply to this message.Thank you.";
					RestTemplate restClient = new RestTemplate();
					SmsRequest smsReq = new SmsRequest(c.getcPhone(), msg);
					ResponseEntity<String> res = restClient.postForEntity("http://localhost:7070/api/v1/sms", smsReq, String.class);
					System.out.println("Respone - " + res.getBody());
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
