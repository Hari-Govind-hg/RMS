package com.ibm.rms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.rms.model.Job;
import com.ibm.rms.repository.JobRepository;
import com.mongodb.DB;
//import com.mongodb.client.MongoClient;
import com.mongodb.MongoClient;

@Service
public class JobService {
	
	@Autowired
	JobRepository jobRepo;
	SequenceGeneratorService sequenceGeneratorService;
	
	 MongoClient mongoClient = new MongoClient();
	 DB db = mongoClient.getDB("test");
	
	public boolean jobCreate(Job job) {
		try {
			job.setjId(sequenceGeneratorService.generateSequence(db,Job.SEQUENCE_NAME));
			jobRepo.save(job);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Job> getAll() {
		return jobRepo.findAll();
	}

	public Job getById(String id) {
		return jobRepo.findById(id).get();
	}

	public boolean updateJob(Job updatedJob) {
		try {
			jobRepo.save(updatedJob);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteJob(String id) {
		try {
			jobRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<Job> filter(String skill) {
		ArrayList<Job> filteredJobs = new ArrayList<Job>();
		ArrayList<Job> allJobs = (ArrayList<Job>) jobRepo.findAll();
		allJobs.forEach(a -> {
			if(a.getSkillList().contains(skill)) {
				filteredJobs.add(a);
			}
		});
		return filteredJobs;
	}
}
