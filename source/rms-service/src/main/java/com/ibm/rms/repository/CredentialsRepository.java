package com.ibm.rms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ibm.rms.model.Candidate;
import com.ibm.rms.model.Credentials;

import java.util.Optional;

public interface CredentialsRepository extends MongoRepository<Credentials, Object> {
    Optional<Credentials> findByUsername(String username);
   }
