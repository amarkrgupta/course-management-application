package com.jrp.pma.entities;

import java.util.ArrayList;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="project_seq")
	@SequenceGenerator(name = "project_seq", sequenceName = "project_seq",
    allocationSize = 1,initialValue=1)
	private long projectId;
	
	private String name;
	
	private String stage; //COMPLETED, INPROGRESS, NOTSTARTED
	
	private String description;
	
	@ManyToMany(cascade={CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH},
			fetch = FetchType.LAZY)
	@JoinTable(name="project_employee",
			joinColumns =@JoinColumn(name="project_id"),
			inverseJoinColumns = @JoinColumn(name="employee_id")
	)
	@JsonIgnore  //This will not show any employees associated with the project
	private List<Employee> employees;
	
	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public Project()
	{
		
	}

	public Project(String name, String stage, String description) {  //The DB will assign the projectId value as not present in constructor
		super();
		this.name = name;
		this.stage = stage;
		this.description = description;
	}

	//convenience method:
	public void addEmployee(Employee emp)
	{
		if(employees==null)
		{
			employees=new ArrayList<>();
		}
		
		employees.add(emp);
	}
	
	
	
	

}
