package com.jrp.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.dao.ProjectRepository;
import com.jrp.pma.entities.Employee;
import com.jrp.pma.entities.Project;
import com.jrp.pma.services.EmployeeService;
import com.jrp.pma.services.ProjectService;

@Controller
@RequestMapping("/projects")
public class ProjectController {
	
	@Autowired
	ProjectService proService;
	
	@Autowired
	EmployeeService empService;

	
	@GetMapping
	public String displayProjects(Model model)
	{
		// we are querying the database for projects
		List<Project> projects = proService.findAll();
		model.addAttribute("projectsList",projects);
		
				
		return "projects/displayProjects";
	}
	
	
	@GetMapping("/new")
	public String displayProjectForm(Model model) //Whenever we need to bind the Java code with the HTML code we use Model as arguments
	{
		Project aProject = new Project();
		List<Employee> employees=empService.findAll();
		model.addAttribute("project",aProject); //The key in this key-value pair must match with the object in HTML code , then only we will be able to bind.
		model.addAttribute("allEmployees",employees); //The key must match with the html code.
		return "projects/new-project";
	}
	
	@PostMapping("/save")
	public String createProject(Project project /*, @RequestParam List<Long> employees ,*/ , Model model)
	{
		proService.save(project);
		
/*
 * Only needed when there is One to Many relationship b/w Project and Employee
		Iterable<Employee> chosenEmployees = empRepo.findAllById(employees);
		
		for(Employee emp:chosenEmployees) 
		{
			emp.setProject(project);
			empRepo.save(emp); //empRepo is our data access object that is we can interact with db via it.
		}
		
*/
		
		// To prevent duplicate submissions
		return "redirect:/projects";
	}
	


}
