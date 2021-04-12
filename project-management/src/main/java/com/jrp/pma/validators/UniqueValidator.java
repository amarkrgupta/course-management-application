package com.jrp.pma.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.jrp.pma.dao.EmployeeRepository;
import com.jrp.pma.entities.Employee;

//Here in <> args we specify the Constraint as well as the type of field
public class UniqueValidator implements ConstraintValidator<UniqueValue, String>{

	//We also need an empRepo object to query from the db whether the email exists or not.
	@Autowired
	EmployeeRepository empRepo;
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		System.out.println("Entered a validation method");
		Employee emp = empRepo.findByEmail(value);
		
		if(emp != null)  //means it's already populated
			return false;
		else
			return true;

	}
	
	

}
