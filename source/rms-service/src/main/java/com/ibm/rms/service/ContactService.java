package com.ibm.rms.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibm.rms.exception.RmsApplicationException;
import com.ibm.rms.model.Contact;
import com.ibm.rms.repository.ContactRepository;
import com.mongodb.DB;
//import com.mongodb.client.MongoClient;
import com.mongodb.MongoClient;

@Service
public class ContactService {
	
	@Autowired
	ContactRepository contactRepo;
	SequenceGeneratorService sequenceGeneratorService;
	
	 MongoClient mongoClient = new MongoClient();
	 DB db = mongoClient.getDB("rms");
	
	public boolean contactCreate(Contact contact) throws RmsApplicationException {
		try {
			contact.setId(sequenceGeneratorService.generateSequence(db,Contact.SEQUENCE_NAME));
			contactRepo.save(contact);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RmsApplicationException("Failed to submit your query; Please try after some time", e);
		}
	}

	public List<Contact> getAll() {
		return contactRepo.findAll();
	}

	public Contact getById(String id) {
		return contactRepo.findById(id).get();
	}

	public boolean updateJob(Contact updatedContact) {
		contactRepo.save(updatedContact);
		return true;
	}

	public boolean deleteContact(String id) {
		contactRepo.deleteById(id);
		return true;
	}

	
}