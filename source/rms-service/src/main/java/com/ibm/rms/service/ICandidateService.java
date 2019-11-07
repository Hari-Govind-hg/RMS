package com.ibm.rms.service;

import java.util.List;

import javax.validation.Valid;

import com.ibm.rms.exception.RmsApplicationException;
import com.ibm.rms.model.Candidate;
import com.ibm.rms.model.Job;

public interface ICandidateService {
	
	public boolean candidateCreate(@Valid Candidate candidate) throws RmsApplicationException;
	
	public List<Job> getAllAppliedJobs(String id) throws RmsApplicationException;
	
	public List<Job> getAllJobsForApply() throws RmsApplicationException;
	
	public List<Job> getJobsByPreference(String id) throws RmsApplicationException;
	
	public boolean applyForJob(String id, String jid) throws RmsApplicationException;
	
	public Candidate getCandidateById(String id);
	
	public boolean updateProfile(Candidate c) throws RmsApplicationException;
	
	public Candidate findByCandidate(@Valid String candidateName);
}
