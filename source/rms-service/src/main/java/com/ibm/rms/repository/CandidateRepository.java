package com.ibm.rms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ibm.rms.model.Candidate;
public interface CandidateRepository extends MongoRepository<Candidate, Object> {
	 
	public Candidate findBycName(String cName);
}
