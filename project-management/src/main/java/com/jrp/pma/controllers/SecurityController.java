package com.jrp.pma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.jrp.pma.dao.UserAccountRepository;
import com.jrp.pma.entities.UserAccount;

@Controller
public class SecurityController {
	
	@Autowired
	UserAccountRepository accountRepo;
	
	@Autowired
	BCryptPasswordEncoder bCryptEncoder;
	
	//This method was to register a user
	@GetMapping("/register")
	public String register(Model model)
	{
		// We have to pass an empty userAccount object to the registration form
		UserAccount userAccount = new UserAccount();
		model.addAttribute("userAccount",userAccount);
		
		return "security/register";
	}
	
	//We could have also used a service class to abstract the repository layer 
	
	//This method is for saving the user
	@PostMapping("/register/save")
	public String saveUser(Model model, UserAccount userAccount)
	{
		//Encoding the password before saving it into the database
		userAccount.setPassword(bCryptEncoder.encode(userAccount.getPassword()));
		accountRepo.save(userAccount);
		return "redirect:/";
		
	}

}
