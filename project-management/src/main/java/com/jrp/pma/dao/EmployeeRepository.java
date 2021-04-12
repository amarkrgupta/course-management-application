package com.jrp.pma.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entities.Employee;

@RepositoryRestResource(collectionResourceRel = "apiemployees",path="apiemployees ")
public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long>{
	
	@Override
	public List<Employee> findAll();
	
	@Query(nativeQuery=true,value="SELECT e.first_name AS firstName,e.last_name AS lastName, COUNT(pe.employee_id) AS projectCount "
			+ "FROM employee e left join project_employee pe ON pe.employee_id=e.employee_id "
			+ "GROUP BY e.first_name,e.last_name ORDER BY 3 DESC") 
	//nativeQuery=true: It is not gonna be specific to spring JPA
	//While copy-pasting the query get rid of /n and add space as we want to give the raw query
	//The rows obtained after firing the query , need to be stored in a data transfer object(dto), an object that represent each row(containg the 3 cols)
	//for this just define the interface and define the property values with names matching the names of the columns
	//Spring data makes it easy and we don't need to define POJO classes
	//now we need to make changes to our homeController
	// then to home.html
	
	public List<EmployeeProject> getEmployeeProjectsCount();

	//Here since we specified in the required format, Spring is smart enough to return an employee based on email!
	//Also if we had named it 'findEmployeeByEmail' then in that case as well spring would have returned the same thing.
	public Employee findByEmail(String value);

	public Employee findByEmployeeId(long theId);

	public void delete(Employee theEmp);  

}
