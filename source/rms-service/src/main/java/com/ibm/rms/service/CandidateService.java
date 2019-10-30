package com.ibm.rms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ibm.rms.exception.RmsApplicationException;
import com.ibm.rms.model.Candidate;
import com.ibm.rms.model.EmailMessage;
import com.ibm.rms.model.Job;
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
	ArrayList<Job> appliedJobList = new ArrayList<Job>();
	
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

	public List<Job> getAllAppliedJobs(String id) {
		candidate = candidateRepo.findById(id).get();
		jobList = (ArrayList<Job>) jobRepo.findAll();
		jobList.forEach( j -> {
			ArrayList<Candidate> candidateList = j.getjAppliedCandidateList();
			if(candidateList.contains(candidate)) {
				appliedJobList.add(j);
			}
		});
//		Optional<Candidate> c = candidateRepo.findById(id);
		return appliedJobList;
	}

	public List<Job> getAllJobsForApply() {
		return jobRepo.findAll();
	}
	
	public List<Job> getJobsByPreference(String id) {
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
	}

	public boolean applyForJob(String id, String jid) throws RmsApplicationException {
		candidate = candidateRepo.findById(id).get();
		
//		candidateRepo.findAll(Query.query(Criteria.where("jAppliedCandidateList.cid").is(id)))
//		System.out.println(candidate);
		job = jobRepo.findById(jid).get();
//		System.out.println(job);
		if(!job.getjAppliedCandidateList().contains(candidate)) {
			job.getjAppliedCandidateList().add(candidate);
			jobRepo.save(job);
//			System.out.println(job.getjAppliedCandidateList());
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setTo_address(candidate.getcEmail());
			emailMessage.setSubject("Your job application update");
			emailMessage.setBody("Dear " +candidate.getcName()+","+ "\r\n" + 
					"Congratulations, you have successfully applied for " +job.getjTitle()+"from "+" IBM"+".");
			emailService.sendmail(emailMessage);
			return true;
		}
//		System.out.println(job.getjAppliedCandidateList().contains(candidate));
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
		return candidateRepo.findBycName(candidateName);
	}

}
