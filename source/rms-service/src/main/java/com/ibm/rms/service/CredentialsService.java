package com.ibm.rms.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ibm.rms.model.Candidate;
import com.ibm.rms.model.Credentials;
import com.ibm.rms.repository.CredentialsRepository;
import com.ibm.rms.repository.CandidateRepository;


import java.util.Optional;

@Service
public class CredentialsService implements UserDetailsService {

    @Autowired
    CredentialsRepository credentialsRepository;
    
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
    	System.out.println(username);
    	System.out.println("Inside loadByUserName");
        Credentials candidate = credentialsRepository.findByUsername(username).get();
        
        System.out.println(candidate.getUsername());
        System.out.println(candidate.getPassword());
        System.out.println(candidate.getRole());
       
        return new User(candidate.getUsername(), candidate.getPassword(), AuthorityUtils.createAuthorityList("ROLE_" + candidate.getRole()));
        

        
      
        
        
//UserAccount userAccount =  this.userRepository.findByUserName(userName).get();
		
		
    }
}
