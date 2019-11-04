package com.ibm.rms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.rms.model.Contact;

public interface ContactRepository extends MongoRepository<Contact, Object> {
	

}