package com.ibm.rms.model;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


	/**
	 * Models employee object
	 */
	@Document("job")
	@XmlRootElement
	public class Job {

		
		@Id
		private int jobId;

		private String designation;
		private String jobRole;
		private String jobDescription;
		private String employmentType;
		private String salary;
		private String location;

		
		public Job()
		{
			
		}


		public int getJobId() {
			return jobId;
		}


		public void setJobId(int jobId) {
			this.jobId = jobId;
		}


		public String getDesignation() {
			return designation;
		}


		public void setDesignation(String designation) {
			this.designation = designation;
		}


		public String getJobRole() {
			return jobRole;
		}


		public void setJobRole(String jobRole) {
			this.jobRole = jobRole;
		}


		public String getJobDescription() {
			return jobDescription;
		}


		public void setJobDescription(String jobDescription) {
			this.jobDescription = jobDescription;
		}


		public String getEmploymentType() {
			return employmentType;
		}


		public void setEmploymentType(String employmentType) {
			this.employmentType = employmentType;
		}


		public String getSalary() {
			return salary;
		}


		public void setSalary(String salary) {
			this.salary = salary;
		}


		public String getLocation() {
			return location;
		}


		public void setLocation(String location) {
			this.location = location;
		}


		public Job(int jobId, String designation, String jobRole, String jobDescription, String employmentType,
				String salary, String location) {
			super();
			this.jobId = jobId;
			this.designation = designation;
			this.jobRole = jobRole;
			this.jobDescription = jobDescription;
			this.employmentType = employmentType;
			this.salary = salary;
			this.location = location;
		}
		
		
	}