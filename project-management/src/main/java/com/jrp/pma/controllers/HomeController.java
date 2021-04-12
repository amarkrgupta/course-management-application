package com.jrp.pma.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.dao.ProjectRepository;
import com.jrp.pma.dto.ChartData;
import com.jrp.pma.dto.EmployeeProject;
import com.jrp.pma.entities.Project;
import com.jrp.pma.services.EmployeeService;
import com.jrp.pma.services.ProjectService;

@Controller
public class HomeController {
	
	@Value("${version}")
	private String ver;
	
	@Autowired
	ProjectService proService;
	
	@Autowired
	EmployeeService empService;
	
	
	@GetMapping("/")
	public String displayHome(Model model) throws JsonProcessingException
	{
		
		model.addAttribute("versionNumber",ver);
		
		// we are querying the database for projects
		List<Project> projects = proService.findAll();
		model.addAttribute("projectsList",projects);
		
		//the data will also be used in Chart
		List<ChartData> projectData = proService.getProjectStatusCount();
		
		//Let's convert project data object to JSON to be able to use in Java Script
		
		ObjectMapper objectMapper=new ObjectMapper();
		String jsonString = objectMapper.writeValueAsString(projectData);
		// [["NOTSTARTED", 1], ["INPROGRESS", 2], ["COMPLETED", 1]]
		
		model.addAttribute("projectStatusCnt", jsonString);

		
		
		// we are querying the database for employees
		List<EmployeeProject> employeesProjectCnt = empService.getEmployeeProjectsCount();
		model.addAttribute("employeesListProjectCnt",employeesProjectCnt);
		
				
		return "main/home";
	}
	
	

}
