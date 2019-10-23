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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import com.ibm.rms.model.Credentials;
import com.ibm.rms.repository.CredentialsRepository;
import com.ibm.rms.repository.CandidateRepository;






@RestController
@CrossOrigin("*")
public class HomeController {
	
	@Autowired
    CredentialsRepository candidateCredentialsRepository;
	
	@Autowired
	CredentialsRepository credentialsRepository;
	
	@Autowired
	Credentials credentials;

	
	
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
    
    @PostMapping(path="/register" ,consumes = { MediaType.APPLICATION_JSON_VALUE })
	public void createEmployee(@RequestBody @Valid Credentials cred)
    	{
    	String encodedPassword = bCryptPasswordEncoder.encode(cred.getPassword());
    	System.out.println(encodedPassword);
    	credentials.setPassword(encodedPassword);
    	
    	System.out.println(cred.getUsername());
    	System.out.println(cred.getPassword());
    	
    	credentials.setUsername(cred.getUsername());
    	credentials.setPassword(encodedPassword);
    	credentials.setRole("CANDIDATE");
    	
    
    	System.out.println("POST");
    	System.out.println(cred.getUsername());
    	System.out.println(cred.getPassword());
    	
    	 
		
		credentialsRepository.save(credentials);
		

//		return ("<h1>Registered</h1>");

	}
    
    


}


