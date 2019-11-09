package com.ibm.rms.repository;

import javax.validation.Valid;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibm.rms.model.Candidate;

public interface CandidateRepository extends MongoRepository<Candidate, Object> {

	Candidate findByUsername(String cName);

}
