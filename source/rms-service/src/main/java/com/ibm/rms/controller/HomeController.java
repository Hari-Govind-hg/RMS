package com.ibm.rms.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ibm.rms.model.Credentials;
import com.ibm.rms.repository.CredentialsRepository;
import com.ibm.rms.service.CandidateService;
import com.ibm.rms.service.CredentialsService;
import com.ibm.rms.repository.CandidateRepository;

@RestController
@CrossOrigin("*")
public class HomeController {

	@Autowired
	CredentialsRepository candidateCredentialsRepository;

	@Autowired
	CredentialsService credentialsService;
	
	@Autowired
	CandidateService candidateService;
	
	@Autowired
	CredentialsRepository credentialsRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping("/")
	public String home() {
		System.out.println("Inside /");
		return ("<h1>Welcome To RMS APP</h1>");
	}

	@PostMapping("/authenticate")
	public Principal hrLogin(Principal user) {
		System.out.println("Authentication successful");
		return user;
	}

	@GetMapping("/hr")
	public String admin() {
		return ("<h1>Welcome HR</h1>");
	}
	
	@PutMapping(path ="/resetpassword", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public boolean resetPassword(@RequestBody @Valid Credentials cred) {
		
		System.out.println("Inside reset password");
		
		Credentials credentials = credentialsService.findByUsername(cred.getUsername());
		
		String encodedPassword = bCryptPasswordEncoder.encode(cred.getPassword());
		
		credentials.setPassword(encodedPassword);
		
		credentialsRepository.save(credentials);
		
		if(credentials!=null)
			return true;
		else
			return false;	
	}

	@PostMapping(path ="/forgotpassword", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public boolean forgotPassword(@RequestBody @Valid Credentials cred) {
		
		boolean res=false;
		
		Credentials credentials = credentialsService.findByUsername(cred.getUsername());
		
		if(credentials==null) {
			res=false;
		}
		else if(credentials.getAnswer().equals(cred.getAnswer())) { 
			res=true;
			System.out.println("Inside else if");
		}
		else {
			res=false	;
		}		
		return res;
	}
	
	
	
	@PostMapping(path = "/register", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void createEmployee(@RequestBody @Valid Credentials cred) {
		
		String encodedPassword = bCryptPasswordEncoder.encode(cred.getPassword());
		
		cred.setPassword(encodedPassword);
		
		credentialsRepository.save(cred);
	}

}
