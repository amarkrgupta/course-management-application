package com.jrp.pma.logging;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/*
 * Aspect is something which you would like to run as a cross-cutting concern
 */

@Aspect
@Component //To add this aspect to our Spring context
public class ApplicationLoggerAspect {
	
	//Define a logger
	//SL4J logger should be imported
	// We can use this 'log' object throughout our application to format our logging
	//And all of that code should only be defined in this class
	//Hence we are modularising it. We want the logging to happen throughout our code but for that purpose we are putting the code for same in just one place only - "ApplicationLoggerAspect"
	
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	//A point cut is basically a location in which u want ur cross cutting code to run
	//SO basically whenever u hear pointcut just think it means 'where' the code for logging should run
	
	@Pointcut("within(com.jrp.pma.controllers..*)")  //you can add another point cut using '||'
	//"..*" means inside controller package the class, the interfaces and everything inside of it
	
	public void definePackagePointcuts()
	{
		// empty method just to name the location specified in pointcut
		
	}
	
	//Now we'll define a method log() and specify the details on the format in which we want logging to happen
	//But to connect log() to our pointcuts, we need to specify "Advise annotations" such as @After,@Before,@Around,etc. on top of our log()
	//Advise because we are advising the method to run either before or after a particular point cut!
	//Now this @After means that log() will run after every single thing in controllers 
//	@Before("definePackagePointcuts()") //In brackets u define the pointcut's definition
//	 public void logBefore(JoinPoint jp)
//	{
//		log.debug("******************* BEFORE METHOD EXECUTION ******************* \n {}.{}() with arguments[s] = {}",
//				jp.getSignature().getDeclaringTypeName(),
//				jp.getSignature().getName(), Arrays.toString(jp.getArgs()));
//		log.debug("------------------------------------------------------------------------------------- \n\n\n");
//	}
//	
//	//JoinPoint is simply a point during the execution of ur program using which u can extract the method name, signaturedefinition etc that helps in ur logging.
//	
//	
//	@After("definePackagePointcuts()") //In brackets u define the pointcut's definition
//	 public void logAfter(JoinPoint jp)
//	{
//		log.debug("******************* AFTER METHOD EXECUTION ******************* \n {}.{}() with arguments[s] = {}",
//				jp.getSignature().getDeclaringTypeName(),
//				jp.getSignature().getName(), Arrays.toString(jp.getArgs()));
//		log.debug("------------------------------------------------------------------------------------- \n\n\n");
//	}
	
	//Next advise annotation is @Around, using which u get more control over JoinPoint and u can log fpr befpre as well as after logging features
	//ProceedingJoinPoint is special JoinPoint that is used to control what runs before and what runs after a particular method
	@Around("definePackagePointcuts()")
	public Object logAround(ProceedingJoinPoint jp)
	{
		log.debug("******************* BEFORE METHOD EXECUTION ******************* \n {}.{}() with arguments[s] = {}",
			jp.getSignature().getDeclaringTypeName(),
			jp.getSignature().getName(), Arrays.toString(jp.getArgs()));
		log.debug("------------------------------------------------------------------------------------- \n\n\n");
		
		Object o =null;
		try {
			 o= jp.proceed(); //proceed needs to be within try-catch as it throws exception
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.debug("******************* AFTER METHOD EXECUTION ******************* \n {}.{}() with arguments[s] = {}",
				jp.getSignature().getDeclaringTypeName(),
				jp.getSignature().getName(), Arrays.toString(jp.getArgs()));
			log.debug("------------------------------------------------------------------------------------- \n\n\n");
			
			return o;
	}
}	
	
	
