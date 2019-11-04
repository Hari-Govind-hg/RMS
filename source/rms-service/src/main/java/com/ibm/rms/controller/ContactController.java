package com.ibm.rms.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibm.rms.model.Contact;
import com.ibm.rms.model.ResponseMessage;
import com.ibm.rms.service.ContactService;

@RestController
@RequestMapping("/contacts")
public class ContactController {
	
	@Autowired
	ContactService contactService;
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> createContact(@RequestBody @Valid Contact contact){

		ResponseMessage resMsg;
		//System.out.println(job);
		// Exception Handling moved to @ExceptionHandler
//		try {
		contactService.contactCreate(contact);
//		} catch (ApplicationException e) {
//			resMsg = new ResponseMessage("Failure", e.getMessage());
//			return ResponseEntity.badRequest().body(resMsg);
//		}

		// Exception Handling moved to @ExceptionHandler
//		if(bindingResult.hasErrors()) {
//			resMsg = new ResponseMessage("Failure", "Validation Error");
//			return ResponseEntity.badRequest().body(resMsg);			
//		}

		resMsg = new ResponseMessage("Success", new String("Job created successfully"));

		// Build newly created Employee resource URI - Employee ID is always 0 here.
		// Need to get the new Employee ID.
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(contact.getId()).toUri();

		return ResponseEntity.created(location).body(resMsg);

	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public List<Contact> getAllEmployees() {
		return contactService.getAll();
	}
	
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public Contact getEmployee(@PathVariable String id) {
		return contactService.getById(id);
	}
	
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> updateEmployee(@PathVariable String id, @RequestBody Contact updatedContact) {
		ResponseMessage resMsg;
		updatedContact.setId(id);
		contactService.updateJob(updatedContact);
		resMsg = new ResponseMessage("Success", new String("Employee updated successfully"));

		// Build newly created Employee resource URI - Employee ID is always 0 here.
		// Need to get the new Employee ID.
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(updatedContact.getId()).toUri();

		return ResponseEntity.created(location).body(resMsg);
	}
	
	@DeleteMapping("/{id}")
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> deleteEmployee(@PathVariable String id) {
		ResponseMessage resMsg;
		contactService.deleteContact(id);
		resMsg = new ResponseMessage("Success", new String("Employee deleted successfully"));
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(id).toUri();

		return ResponseEntity.created(location).body(resMsg);
	}
	
	
}
