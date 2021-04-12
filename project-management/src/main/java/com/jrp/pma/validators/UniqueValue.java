package com.jrp.pma.validators;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)  //This specifies that the custom annotation will be applicable on a FIELD, and not other options like CLASS,etc.
@Retention(RetentionPolicy.RUNTIME) //Ensures that custom annotation is available in the byte code , so Java can read it using reflection mechanism.

//The above two are typically used for any annotation creation
//Now we will use specific to the Constraint

@Constraint(validatedBy=UniqueValidator.class) //Here we specify the name of the class where we have defined our validation rules!
public @interface UniqueValue {
	
	//Here we need a couple of things - 1. message field,
	
	String message() default "Unique Constraint Violated"; //Message appears when there is error
	
	//Then , define groups and pay-load
	Class<?>[] groups() default{};
	
	Class<? extends Payload>[] payload() default{};

}
