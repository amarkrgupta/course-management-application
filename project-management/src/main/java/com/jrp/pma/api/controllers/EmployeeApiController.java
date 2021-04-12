package com.jrp.pma.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.entities.Employee;

@RestController
@RequestMapping("/app-api/employees")
public class EmployeeApiController {
	
	@Autowired
	EmployeeRepository empRepo;  //TO access our data in the employee table
	
	@GetMapping
	public List<Employee> getEmployees()
	{
		return empRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Employee getEmployeeById(@PathVariable("id") Long id)
	{
		return empRepo.findById(id).get();
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Employee create(@RequestBody @Valid Employee employee)
	{
		return empRepo.save(employee);
	}
	
	//Specify id in the body else it will add a new employee
	//Also it is used to update the entire object and hence all its relation with project is deleted due to cascade delete	
	@PutMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Employee update(@RequestBody Employee employee)
	{
		return empRepo.save(employee);
	}
	
	//This updates the Employee object while preserving all the relations with the project
	@PatchMapping(path="/{id}",consumes = "application/json")
	public Employee partialUpdate(@PathVariable("id") Long id,  @RequestBody @Valid Employee patchEmployee)
	{
		Employee emp = empRepo.findById(id).get();
		
		if(patchEmployee.getEmail()!=null)
		{
			emp.setEmail(patchEmployee.getEmail());
		}
		if(patchEmployee.getFirstName()!=null)
		{
			emp.setFirstName(patchEmployee.getFirstName());
		}
		if(patchEmployee.getLastName()!=null)
		{
			emp.setLastName(patchEmployee.getLastName());
		}
		
		return empRepo.save(emp);
	}
	

	@DeleteMapping(path="/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Long id)
	{
		//try-catch is to prevent trying to delete something which doesn't exist making the delete method idempotent
		
		try
		{
			empRepo.deleteById(id);
		}
		catch(EmptyResultDataAccessException e)
		{
			
		}
	}
	
	@GetMapping(params= {"page", "size"})
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Employee> findPaginatedEmployees(@RequestParam("page") int page, @RequestParam("size") int size)
	{
		Pageable pageAndSize = PageRequest.of(page, size);
		return empRepo.findAll(pageAndSize);
	}
	
}
