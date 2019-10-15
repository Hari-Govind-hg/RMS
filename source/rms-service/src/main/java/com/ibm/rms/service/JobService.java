package com.ibm.rms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.rms.model.Job;
import com.ibm.rms.repository.JobRepository;

@Service
public class JobService {
	
	@Autowired
	JobRepository jobRepo;
	
	public boolean jobCreate(Job job) {
		try {
			jobRepo.save(job);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
