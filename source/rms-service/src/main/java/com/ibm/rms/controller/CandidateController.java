package com.ibm.rms.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibm.rms.exception.RmsApplicationException;
import com.ibm.rms.model.Candidate;
import com.ibm.rms.model.Job;
import com.ibm.rms.model.ResponseMessage;
import com.ibm.rms.service.CandidateService;
import com.ibm.rms.service.EmailService;
import com.ibm.rms.model.EmailMessage;
import com.ibm.rms.service.JobService;

@RestController
@RequestMapping("/candidates")
public class CandidateController {
	
	private static Logger log = LoggerFactory.getLogger(CandidateController.class);
	
	@Autowired
	CandidateService candidateService;
	@Autowired
	EmailService emailService;
//	@Autowired
//	JobService jobService;
	
	
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> createCandidate(@RequestBody @Valid Candidate candidate) throws RmsApplicationException{

		ResponseMessage resMsg;
		//System.out.println(job);
		// Exception Handling moved to @ExceptionHandler
//		try {
		boolean x = candidateService.candidateCreate(candidate);
//		} catch (ApplicationException e) {
//			resMsg = new ResponseMessage("Failure", e.getMessage());
//			return ResponseEntity.badRequest().body(resMsg);
//		}

		// Exception Handling moved to @ExceptionHandler
//		if(bindingResult.hasErrors()) {
//			resMsg = new ResponseMessage("Failure", "Validation Error");
//			return ResponseEntity.badRequest().body(resMsg);			
//		}
		if(x) {
			resMsg = new ResponseMessage("Success", new String ("Candidate created successfully"));
			
		}
		else {
			resMsg = new ResponseMessage("Failed", new String ("Failed to create candidate"));
		}
		// Build newly created Employee resource URI - Employee ID is always 0 here.
		// Need to get the new Employee ID.
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(candidate.getcId()).toUri();

		return ResponseEntity.created(location).body(resMsg);

	}
	
	@GetMapping(value="/{id}/appliedjobs",produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public List<Job> getAllJobs(@PathVariable String id) throws RmsApplicationException {
		return candidateService.getAllAppliedJobs(id);
	}
	
	@GetMapping(value="/applynewjob", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public List<Job> showAllJobsAvailable() throws RmsApplicationException{
		return candidateService.getAllJobsForApply();
	}
	
	@GetMapping(value="/{id}/applynewjobbypreference", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public List<Job> showJobsAvailableByPreference(@PathVariable String id) throws RmsApplicationException{
		return candidateService.getJobsByPreference(id);
	}
	
	@GetMapping(value="/{id}/applyjob", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> applyJob(@PathVariable String id,@RequestParam(value="jid") String jid) throws RmsApplicationException{
		Candidate candidate = candidateService.getCandidateById(id);
		ResponseMessage resMsg;
		boolean x = candidateService.applyForJob(id,jid);
		if(x) {
			resMsg = new ResponseMessage("Success", new String ("Applied for job successfully"));
		}
		else {
			resMsg = new ResponseMessage("Failed", new String ("You have already applied for the job"));
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/appliedjobs")
				.buildAndExpand(candidate.getcId()).toUri();

		return ResponseEntity.created(location).body(resMsg);
	}
	
	@PutMapping(value = "/{id}/profile", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> profileEdit(@PathVariable String id, @RequestBody Candidate c){
		ResponseMessage resMsg;
		c.setcId(id);
		boolean x = candidateService.updateProfile(c);
		if(x) {
			resMsg = new ResponseMessage("Success", new String ("profile updated successfully"));
		}
		else {
			resMsg = new ResponseMessage("Failed", new String ("profile update failed"));
		}
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/appliedjobs")
				.buildAndExpand(c.getcId()).toUri();
		return ResponseEntity.created(location).body(resMsg);
	}
	
	@GetMapping(value = "/{id}/profile", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public Candidate profileView(@PathVariable String id){
		Candidate candidate = candidateService.getCandidateById(id);
		return candidate;
	}
	
	@PostMapping(value="/detail", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public Candidate returnCandidateDetails(@RequestBody @Valid String candidateName){
		System.out.println("Inside details component backend");
		return candidateService.findByCandidate(candidateName);
	}
	
//	@RequestMapping(value="/send", method=RequestMethod.POST)
//	public String sendEmail(@RequestBody EmailMessage emailmessage) throws AddressException, MessagingException, IOException {
//		emailService.sendmail(emailmessage);
//		return "Email sent successfully";
//	}
	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<ResponseMessage> handleRmsApplicationExcpetion(Exception e) {
//		System.out.println("ERROR: " + e.getMessage());
//		log.error("ERROR OCCURED: {}", e.getMessage(), e);
//		ResponseMessage resMsg = new ResponseMessage("Failure", new String[] { e.getMessage() },
//				ExceptionUtils.getStackTrace(e));
//		return ResponseEntity.badRequest().body(resMsg);
//	}
}
