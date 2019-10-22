package com.ibm.rms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibm.rms.model.*;

public interface JobRepository extends MongoRepository<Job, Object>{

}
