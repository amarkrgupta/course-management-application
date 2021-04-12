package com.jrp.pma.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jrp.pma.entities.Employee;
import com.jrp.pma.services.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	EmployeeService empService; 
	
	@GetMapping
	public String displayEmployees(Model model)
	{
		// we are querying the database for employees
		List<Employee> employees = empService.findAll();
		model.addAttribute("employeesList",employees);
		
				
		return "employees/displayEmployees";
	}
	
	@GetMapping("/new")
	public String displayEmployeeForm(Model model)
	{
		Employee emp = new Employee();
		model.addAttribute("employee",emp);
		return "employees/new-employee";
	}
	
	@PostMapping("/save")
	public String saveEmployeeData(Model model, @Valid Employee e , Errors errors )
	{
		if (errors.hasErrors()) 
		{
			System.out.println("--------------- Error handling ------------------");
			return "employees/new-employee";
		}
			

		
		empService.save(e);
		
		return "redirect:/employees/new";
	}
	
	//By default for <a> tag we need to have 'get-mapping'
	@GetMapping("/update")
	public String displayEmployeeUpdateForm(@RequestParam("id") long theId, Model model)
	{
		Employee theEmp = empService.findByEmployeeId(theId);		
		model.addAttribute(theEmp);		
		return "employees/new-employee";
	}
	
	//By default for <a> tag we need to have 'get-mapping'
	@GetMapping("/delete")
	public String deleteEmployee(@RequestParam("id") long theId, Model model)
	{
		Employee theEmp = empService.findByEmployeeId(theId);
		empService.delete(theEmp);
		return "redirect:/employees"; 
	}
	
	
	

}
