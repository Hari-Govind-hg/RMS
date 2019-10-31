package com.ibm.rms.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.ibm.rms.exception.RmsApplicationException;
import com.ibm.rms.model.Job;
import com.ibm.rms.model.ResponseMessage;
import com.ibm.rms.service.JobService;

@RestController
@RequestMapping("/jobs")
public class JobController {
	
	private static Logger log = LoggerFactory.getLogger(JobController.class);
	
	@Autowired
	JobService jobService;
	
	@PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> createJob(@RequestBody @Valid Job job) throws RmsApplicationException{

		ResponseMessage resMsg;
		//System.out.println(job);
		// Exception Handling moved to @ExceptionHandler
//		try {
		jobService.jobCreate(job);
//		} catch (ApplicationException e) {
//			resMsg = new ResponseMessage("Failure", e.getMessage());
//			return ResponseEntity.badRequest().body(resMsg);
//		}

		// Exception Handling moved to @ExceptionHandler
//		if(bindingResult.hasErrors()) {
//			resMsg = new ResponseMessage("Failure", "Validation Error");
//			return ResponseEntity.badRequest().body(resMsg);			
//		}
		resMsg = new ResponseMessage("Success", new String ("Job created successfully."));
		// Build newly created Employee resource URI - Employee ID is always 0 here.
		// Need to get the new Employee ID.
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(job.getjId()).toUri();

		return ResponseEntity.created(location).body(resMsg);

	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public List<Job> getAllEmployees() {
		return jobService.getAll();
	}
	
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public Job getEmployee(@PathVariable String id) {
		return jobService.getById(id);
	}
	
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> updateEmployee(@PathVariable String id, @RequestBody Job updatedJob) throws RmsApplicationException {
		ResponseMessage resMsg;
		updatedJob.setjId(id);
		boolean x = jobService.updateJob(updatedJob);
		if(x) {
			resMsg = new ResponseMessage("Success", new String ("Job updated successfully"));
		}
		else {
			resMsg = new ResponseMessage("Failed", new String ("Job update failed"));
		}
		// Build newly created Employee resource URI - Employee ID is always 0 here.
		// Need to get the new Employee ID.
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(updatedJob.getjId()).toUri();

		return ResponseEntity.created(location).body(resMsg);
	}
	
	@DeleteMapping("/{id}")
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> deleteEmployee(@PathVariable String id) throws RmsApplicationException {
		ResponseMessage resMsg;
		boolean x = jobService.deleteJob(id);
		if(x) {
			resMsg = new ResponseMessage("Success", new String ("Job deleted successfully"));
		}
		else {
			resMsg = new ResponseMessage("Failed", new String ("Job deletion failed"));
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(id).toUri();

		return ResponseEntity.created(location).body(resMsg);
	}
	
	@GetMapping(value = "/filterbyskill", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ArrayList<Job> searchJobsBySkill(@RequestParam(value="skill") String skill){
		ArrayList<Job> jobsList = jobService.filterBySkill(skill);
		return jobsList;
	}
	
	@GetMapping(value = "/filterbyskillandexperience", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ArrayList<Job> searchJobsBySkillAndExperience(@RequestParam(value="skill") String skill,@RequestParam(value="experience") String experience){
	
		ArrayList<Job> jobsList = jobService.filterBySkillAndExperience(skill,experience);
		return jobsList;
	}
	
	@GetMapping(value = "/filterbyexperience", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ArrayList<Job> searchJobsByExperience(@RequestParam(value="experience") String experience){
		ArrayList<Job> jobsList = jobService.filterByExperience(experience);
		return jobsList;
	}
	
	@PutMapping(value="/{id}/schedule", consumes = {MediaType.APPLICATION_JSON_VALUE})
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> scheduleInterview(@PathVariable String id,@RequestBody @Valid Job job) throws RmsApplicationException{
		job.setjId(id);
		jobService.setInterviewDate(job);
		return null;
	}
}
