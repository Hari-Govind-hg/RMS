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
		
		jobService.jobCreate(job);

		resMsg = new ResponseMessage("Success", new String ("Job created successfully."));

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(job.getjId()).toUri();

		return ResponseEntity.created(location).body(resMsg);
	}
	
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public List<Job> getAllJobs() throws RmsApplicationException {
		return jobService.getAll();
	}
	
	@GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public Job getEmployee(@PathVariable String id) throws RmsApplicationException {
		return jobService.getById(id);
	}
	
	@PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> updateJob(@PathVariable String id, @RequestBody Job updatedJob) throws RmsApplicationException {
		
		ResponseMessage resMsg;
		
		updatedJob.setjId(id);
		
		jobService.updateJob(updatedJob);
		
		resMsg = new ResponseMessage("Success", new String ("Job updated successfully"));
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(updatedJob.getjId()).toUri();

		return ResponseEntity.created(location).body(resMsg);
	}
	
	@DeleteMapping("/{id}")
	@CrossOrigin("*")
	public ResponseEntity<ResponseMessage> deleteJob(@PathVariable String id) throws RmsApplicationException {
		
		ResponseMessage resMsg;
		
		jobService.deleteJob(id);
		
		resMsg = new ResponseMessage("Success", new String ("Job deleted successfully"));
		
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
