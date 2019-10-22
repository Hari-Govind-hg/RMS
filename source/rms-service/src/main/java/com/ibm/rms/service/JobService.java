package com.ibm.rms.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ibm.rms.exception.*;
import com.ibm.rms.model.*;
import com.ibm.rms.repository.JobRepository;

@Service
public class JobService {


	@Autowired
	JobRepository jobRepo;

	public JobService() {
	}

	public boolean create(Job job) throws ApplicationException {

		try {
			jobRepo.save(job);
			return true;
		} catch (Exception e) {

			throw new ApplicationException("Server Error. Please try after sometime. Cause: " + e.getMessage(), e);
		}
	}
	
	public List<Job> getAll() {
		return jobRepo.findAll();
	}
	
	public Job get(int id) {
		return jobRepo.findById(id).get();
	}


public boolean update(Job Job) {
		jobRepo.save(Job);
		return true;
	}

	public boolean delete(int id) {
		jobRepo.deleteById(id);
		return true;
	}
}

