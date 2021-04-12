package com.jrp.pma.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jrp.pma.validators.UniqueValue;

@Entity
public class Employee {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="employee_seq")
	@SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq",
    allocationSize = 1,initialValue=1)
	private long employeeId;
	
	@NotBlank(message="Must give a first name")
	@Size(min=1, max=100) 
	private String firstName;
	
	@NotBlank(message="Must give a last name")
	@Size(min=1, max=100) 
	private String lastName;
	
	@NotBlank
	@Email(message="Must be a valid email")  //No need to use regular expression just this annotation is enough.  Coming from javax.validation means the check is happening at the client level, i.e. when we are providing the request body
	//@Column(unique = true) //we don't want two people to have the same emailIds.Comes from javax.persistence, so validation at the db level
	@UniqueValue
	private String email;
	
	@ManyToMany(cascade={CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	@JoinTable(name="project_employee",
	joinColumns =@JoinColumn(name="employee_id"),
	inverseJoinColumns = @JoinColumn(name="project_id")
				)
	@JsonIgnore //This will not show any project associated with the employee to prevent infinite serialization
	private List<Project> projects;
	
	
	public long getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(long employeeId) {
		this.employeeId = employeeId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}		

	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public Employee(String firstName, String lastName, String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	
	public Employee()
	{}

}
