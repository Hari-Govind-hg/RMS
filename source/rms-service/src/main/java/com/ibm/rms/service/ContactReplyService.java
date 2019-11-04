package com.ibm.rms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.rms.model.ContactReply;
import com.ibm.rms.model.EmailMessage;
import com.ibm.rms.repository.ContactReplyRepository;
import com.mongodb.DB;
//import com.mongodb.client.MongoClient;
import com.mongodb.MongoClient;

@Service
public class ContactReplyService {
	
	@Autowired
	ContactReplyRepository contactReplyRepo;
	SequenceGeneratorService sequenceGeneratorService;
	
	@Autowired
	EmailService emailService;
	
	 MongoClient mongoClient = new MongoClient();
	 DB db = mongoClient.getDB("test");
	
	public boolean contactReplyCreate(ContactReply contactReply) {
		try {
			contactReply.setId(sequenceGeneratorService.generateSequence(db,ContactReply.SEQUENCE_NAME));
			contactReplyRepo.save(contactReply);
			EmailMessage emailMessage = new EmailMessage();
			emailMessage.setTo_address(contactReply.getEmail());
			emailMessage.setSubject(contactReply.getSubject());
			emailMessage.setBody(contactReply.getMessage());
			emailService.sendmail(emailMessage);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<ContactReply> getAll() {
			
		return contactReplyRepo.findAll();
	}

	public ContactReply getById(String id) {
		return contactReplyRepo.findById(id).get();
	}

	

	
}
