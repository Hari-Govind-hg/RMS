package com.ibm.rms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.ibm.rms.model.Candidate;
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
	
	Candidate candidate;
	
	Job job;
	SequenceGeneratorService sequenceGeneratorService;
	
	
	ArrayList<String> candidateSkills = new ArrayList<String>();
	ArrayList<Job> jobList = new ArrayList<Job>();
	ArrayList<Job> preferedJobList = new ArrayList<Job>();
	ArrayList<Job> appliedJobList = new ArrayList<Job>();
	
	 MongoClient mongoClient = new MongoClient();
	 DB db = mongoClient.getDB("test");
	
	public boolean candidateCreate(@Valid Candidate candidate) {
		try {
			candidate.setcId(SequenceGeneratorService.generateSequence(db,Candidate.SEQUENCE_NAME));
			candidateRepo.save(candidate);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	public Candidate findByCandidate(String candidateName)
	{
		return candidateRepo.findBycName(candidateName);
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
		jobList = (ArrayList<Job>) jobRepo.findAll();
		ArrayList<Job> preferedJobList = new ArrayList<Job>();
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

	public boolean applyForJob(String id, String jid) {
		candidate = candidateRepo.findById(id).get();
		
//		candidateRepo.findAll(Query.query(Criteria.where("jAppliedCandidateList.cid").is(id)))
		System.out.println(candidate);
		job = jobRepo.findById(jid).get();
		System.out.println(job);
		if(!job.getjAppliedCandidateList().contains(candidate)) {
			job.getjAppliedCandidateList().add(candidate);
			jobRepo.save(job);
			System.out.println(job.getjAppliedCandidateList());
			
			return true;
		}
		System.out.println(job.getjAppliedCandidateList().contains(candidate));
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

}
