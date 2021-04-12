package com.jrp.pma.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entities.Employee;

/*
 * Adding the service layer class to create an extra layer of abstraction between controller and repository layer.
 * So that when we required to move to a NoSQL database, we don't need to make any changes to the controller class. 
 * Hence decoupling repositories from controllers using a service layer. 
 */

@Service
public class EmployeeService {
	
	@Autowired
	EmployeeRepository empRepo;
	
	public Employee save(Employee employee) 
	{
		return empRepo.save(employee);
	}
	
	public List<Employee> findAll()
	{
		return empRepo.findAll();
	}
	
	public List<EmployeeProject> getEmployeeProjectsCount()
	{
		return empRepo.getEmployeeProjectsCount();
	}

	public Employee findByEmployeeId(long theId) {
		
		return empRepo.findByEmployeeId(theId);
	}

	public void delete(Employee theEmp) {
		empRepo.delete(theEmp);
		
	}
	
	
	
	

}
