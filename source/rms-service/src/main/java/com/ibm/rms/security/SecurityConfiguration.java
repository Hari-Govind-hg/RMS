package com.ibm.rms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ibm.rms.model.Candidate;
import com.ibm.rms.model.Credentials;

import com.ibm.rms.model.Job;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Autowired
	UserDetailsService userDetailsService;
    
    @Bean
    public Credentials Createcandidate()
    {
    	Credentials candidateCredentials = new Credentials();
    	return candidateCredentials;
    }
    
   
   
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }
    
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
        System.out.println("Inside configure authentication");
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	  
    	        http.authorizeRequests()
    	        		.antMatchers("/register").permitAll()
                		.antMatchers("/authenticate").permitAll()
    	        		.antMatchers("/").authenticated()
    	        		.antMatchers("/jobs/**").permitAll()
    	                .antMatchers("/hr").hasRole("HR")
    	                .antMatchers("/candidates").permitAll()
    	                .antMatchers("/").permitAll()
    	                
    	                .anyRequest().authenticated()
    	                .and().formLogin()
    	        		.and().httpBasic();
    	        		
    	        http
    			// Disable CSRF check
    			.csrf().disable();
//    			.sessionManagement().disable();
    }

}
