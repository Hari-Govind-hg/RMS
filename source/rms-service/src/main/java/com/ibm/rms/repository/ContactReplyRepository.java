package com.ibm.rms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.rms.model.ContactReply;

public interface ContactReplyRepository extends MongoRepository<ContactReply, Object> {
	

}