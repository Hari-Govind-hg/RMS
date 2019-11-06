package com.ibm.rms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ibm.rms.exception.RmsApplicationException;
import com.ibm.rms.model.Candidate;
import com.ibm.rms.model.EmailMessage;
import com.ibm.rms.model.Job;
import com.ibm.rms.model.SmsRequest;
import com.ibm.rms.repository.CandidateRepository;
import com.ibm.rms.repository.JobRepository;
import com.mongodb.DB;
import com.mongodb.MongoClient;

@Service
public class CandidateService {
	
	@Autowired
	CandidateRepository candidateRepo;
	
	@Autowired
	JobRepository jobRepo;

	@Autowired
	EmailService emailService;
	
	Candidate candidate;
	Job job;
	SequenceGeneratorService sequenceGeneratorService;
	
	
	ArrayList<String> candidateSkills = new ArrayList<String>();
	ArrayList<Job> jobList = new ArrayList<Job>();
	ArrayList<Job> preferedJobList;
	ArrayList<Job> appliedJobList;
	
	 MongoClient mongoClient = new MongoClient();
	 DB db = mongoClient.getDB("test");
	
	public boolean candidateCreate(@Valid Candidate candidate) throws RmsApplicationException {
		try {
			candidate.setcId(SequenceGeneratorService.generateSequence(db,Candidate.SEQUENCE_NAME));
			candidateRepo.save(candidate);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RmsApplicationException("Can not create candidate now; try after sometime",e);
		}
	}

	public List<Job> getAllAppliedJobs(String id) throws RmsApplicationException {
		try {
			 appliedJobList= new ArrayList<Job>();
			candidate = candidateRepo.findById(id).get();
			jobList = (ArrayList<Job>) jobRepo.findAll();
			jobList.forEach( j -> {
				ArrayList<Candidate> candidateList = j.getjAppliedCandidateList();
				if(candidateList.contains(candidate)) {
					appliedJobList.add(j);
				}
			});
//			Optional<Candidate> c = candidateRepo.findById(id);
			return appliedJobList;
		} catch (DataAccessException e) {
			throw new RmsApplicationException("You have not applied for any job", e);
		}
		
	}

	public List<Job> getAllJobsForApply() throws RmsApplicationException {
		try {
			return jobRepo.findAll();
		} catch (DataAccessException e) {
			throw new RmsApplicationException("Can not get any jobs now", e);
		}
	}
	
	public List<Job> getJobsByPreference(String id) throws RmsApplicationException {
		try {
			preferedJobList=new ArrayList<Job>();
			jobList = (ArrayList<Job>) jobRepo.findAll();
			candidateSkills = candidateRepo.findById(id).get().getSkillList();
			jobList.forEach(j -> {
				candidateSkills.forEach(s -> {
					if(j.getSkillList().contains(s)) {
						if(!preferedJobList.contains(j)) {
							preferedJobList.add(j);
						}
					}
				});
			});
			return preferedJobList;
		} catch (Exception e) {
			throw new RmsApplicationException("This service is not available now: Try after some time", e);
		}
	}

	public boolean applyForJob(String id, String jid) throws RmsApplicationException {
		candidate = candidateRepo.findById(id).get();
		job = jobRepo.findById(jid).get();
		if(!job.getjAppliedCandidateList().contains(candidate)) {
			job.getjAppliedCandidateList().add(candidate);
			jobRepo.save(job);
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setTo_address(candidate.getcEmail());
			emailMessage.setSubject("Your job application update");
			emailMessage.setBody("Dear " +candidate.getcName()+","+ "\r\n" + 
					"You have successfully applied for " +job.getjTitle()+" position from IBM. Further contacting will take place shortly, so kindly be patient"+"\n"
					+"Job Title: "+job.getjTitle()+"\n"
					+"Job Description: "+job.getjDescription()+"\n"
					+"Min Experience Required: "+job.getjRequiredExperience()+"\n\n"
					+"Regards,"+"\n"
					+"RMS Team"
					+"\n\n\n\n"
					+"THIS IS A SYTEM GENERATED MAIL.PLEASE DO NOT REPLY TO THIS MAIL.THANK YOU."
					+"For any queries,please feel free to reach us from the Contact Us page of our website");
			emailService.sendmail(emailMessage);
			String msg = "Dear "+candidate.getcName()+"\n"+" You have successfully applied for "+job.getjTitle()+"\n\n"+"Regards,"+"\n"+"RMS Team"+"\n\n"+"This is a system generated response.Please do NOT reply to this message.Thank you.";
			RestTemplate restClient = new RestTemplate();
			SmsRequest smsReq = new SmsRequest(candidate.getcPhone(), msg);
			ResponseEntity<String> res = restClient.postForEntity("http://localhost:7070/api/v1/sms", smsReq, String.class);
			System.out.println("Respone - " + res.getBody());
			
			return true;
		}
		return false;
	}

	public Candidate getCandidateById(String id) {
		return candidateRepo.findById(id).get();
	}

	public boolean updateProfile(Candidate c) {
		try {
			candidateRepo.save(c);
			return true;
		} catch (Exception e) {
			
		}
		return false;
	}

	public Candidate findByCandidate(@Valid String candidateName) {
		return candidateRepo.findByUsername(candidateName);
	}

}
