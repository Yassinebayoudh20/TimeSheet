package tn.esprit.spring.config;



import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.springframework.stereotype.Component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
@Aspect

public class EmployeeLogging {
	private static final Logger logger = LogManager.getLogger(EmployeeLogging.class);
	@Around("execution(* tn.esprit.spring.services.EmployeServiceImpl.*(..))")
	public Object profile(ProceedingJoinPoint pjp) throws Throwable {
	long start = System.currentTimeMillis();
	Object obj = pjp.proceed();
	long elapsedTime = System.currentTimeMillis() - start;
	if(elapsedTime>3000)
	{	logger.info("Method execution time: %1$s milliseconds. " ,elapsedTime);
	}
	
	return obj;
	}

}
