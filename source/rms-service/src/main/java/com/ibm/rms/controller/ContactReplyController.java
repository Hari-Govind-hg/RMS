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

import com.ibm.rms.model.ContactReply;
import com.ibm.rms.model.ResponseMessage;
import com.ibm.rms.service.ContactReplyService;

@RestController
@RequestMapping("/contactReply")
public class ContactReplyController {
	
	@Autowired
	ContactReplyService contactReplyService;
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> createContactReply(@RequestBody @Valid ContactReply contactReply){

		ResponseMessage resMsg;
//		System.out.println(contactReply);
		//System.out.println(job);
		// Exception Handling moved to @ExceptionHandler
//		try {
		contactReplyService.contactReplyCreate(contactReply);
//		} catch (ApplicationException e) {
//			resMsg = new ResponseMessage("Failure", e.getMessage());
//			return ResponseEntity.badRequest().body(resMsg);
//		}

		// Exception Handling moved to @ExceptionHandler
//		if(bindingResult.hasErrors()) {
//			resMsg = new ResponseMessage("Failure", "Validation Error");
//			return ResponseEntity.badRequest().body(resMsg);			
//		}

		resMsg = new ResponseMessage("Success", new String[] {"Job created successfully"});
		

		// Build newly created Employee resource URI - Employee ID is always 0 here.
		// Need to get the new Employee ID.
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(contactReply.getId()).toUri();

		
		return ResponseEntity.created(location).body(resMsg);
		

	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public List<ContactReply> getAllEmployees() {
		return contactReplyService.getAll();
	}
	
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ContactReply getEmployee(@PathVariable String id) {
		return contactReplyService.getById(id);
	}
	
	
}