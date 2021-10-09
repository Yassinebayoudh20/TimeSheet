package tn.esprit.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    //Logging Calculated Execution Time for all test methods
    @Around("execution(* tn.esprit.spring.services.EntrepriseServiceImpl.*(..))")
    public Object logExecutionTimeForTestMethodsAspect(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("Request for {},{}() with arguments={}",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));

        Instant start = Instant.now();

        Object obj = joinPoint.proceed();

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start,finish).toMillis();

        if(timeElapsed > 3000){
            log.warn("Method {} is taking more than 3 seconds to execute",joinPoint.getSignature().getName());
        }

        log.info("Response for {},{}() with Result={}",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                obj.toString());

        log.info("Time taken to execute {} was {}",
                joinPoint.getSignature().getName(),
                new SimpleDateFormat("mm:ss:SSS").format(new Date(timeElapsed)));

        return obj;
    }

    //Logging Object to add details
    @Before("execution(* tn.esprit.spring.services.IEntrepriseService.ajouterEntreprise(..))")
    public void createEntrepriseLoggerAspect(JoinPoint joinPoint){
        log.info("Creating Object Entreprise {}",
                Arrays.stream(joinPoint.getArgs()).findFirst().get());
    }

    //Logging Method Result
    @AfterReturning(value = "execution(* tn.esprit.spring.services.IEntrepriseService.ajouterEntreprise(..))" , returning = "result")
    public void FinishcreateEntrepriseLoggerAspect(JoinPoint joinPoint , Object result){
        if(result == null || (int) result <= 0){
            log.error("Object {} has not been added to database with result of {}",
                    Arrays.stream(joinPoint.getArgs()).findFirst().get(),
                    result);
        }else {
            log.info("Object {} has been added successfully",
                    Arrays.stream(joinPoint.getArgs()).findFirst().get());
        }
    }
}
