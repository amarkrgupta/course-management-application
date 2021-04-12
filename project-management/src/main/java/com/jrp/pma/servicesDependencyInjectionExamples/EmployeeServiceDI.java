package com.jrp.pma.servicesDependencyInjectionExamples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service //necessary for component scanning
public class EmployeeServiceDI {

/*
 * Field Injection
	@Autowired
	EmployeeRepository empRepo;

*/
	

/* Constructor Injection 
	
	EmployeeRepository empRepo;

	public EmployeeService(EmployeeRepository empRepo) {
		super();
		this.empRepo =empRepo;

	}
	
*/

/*	Setter Injection
	
	EmployeeRepository empRepo;
	
	@Autowired
	public void setEmpRepo(EmployeeRepository empRepo) {
		this.empRepo = empRepo;
	}
	
*/
	@Autowired	
	IStaffRepository staffRepo;

}
