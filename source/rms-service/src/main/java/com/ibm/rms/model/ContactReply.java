package com.ibm.rms.model;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("contactReply")
public class ContactReply {
	@Transient
    public static final String SEQUENCE_NAME = "contactReply_sequence";
	
	private String id;
	private String email;
	private String subject;
	
	private String message;
	
	public ContactReply() {
		super();
	}
	
	public ContactReply(String email,String subject,String message) {
		super();
		this.id=id;
		this.email=email;
		this.subject=subject;
		this.message=message;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "[id="+ id + ",email=" + email + ", subject=" + subject + ", message=" + message + "]";
	}
}