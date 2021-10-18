package tn.esprit.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import tn.esprit.spring.entities.Entreprise;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@Component
@Aspect
@Slf4j
public class EntrepriseLoggingAspect {

    //Logging Calculated Execution Time for all test methods
    @Around("execution(* tn.esprit.spring.services.EntrepriseServiceImpl.*(..))")
    public Object logExecutionTimeForTestMethodsAspect(ProceedingJoinPoint joinPoint) throws Throwable{
        log.debug("Request for {},{}() with arguments={}",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));

        Instant start = Instant.now();

        Object obj = joinPoint.proceed();

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start,finish).toMillis();

        if(timeElapsed > 3000){
            log.info("Method {} is taking more than 3 seconds to execute",joinPoint.getSignature().getName());
        }

        log.debug("Response for {},{}() with Result={}",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                obj.toString());

        log.info("Time taken to execute {} was {}",
                joinPoint.getSignature().getName(),
                new SimpleDateFormat("mm:ss:SSS").format(new Date(timeElapsed)));

        return obj;
    }

    //Logs for Ajouter and Modifier Entreprise
    @Before("execution(* tn.esprit.spring.services.IEntrepriseService.*(tn.esprit.spring.entities.Entreprise))")
    public void createEntrepriseLoggerAspect(JoinPoint joinPoint){
        Optional<Entreprise> entrepriseOptional = (Optional<Entreprise>) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        if(entrepriseOptional.isPresent()){
            Entreprise entreprise = entrepriseOptional.get();
            log.info("Creating Object Entreprise {}",entreprise);
        }else log.error("Creating Object Not Present to create");
    }

    //Logging Method Result
    @AfterReturning(value = "execution(* tn.esprit.spring.services.IEntrepriseService.ajouterEntreprise(..))" , returning = "result")
    public void finishcreateEntrepriseLoggerAspect(JoinPoint joinPoint , Object result){
        Optional<Entreprise> entrepriseOptional = (Optional<Entreprise>) Arrays.stream(joinPoint.getArgs()).findFirst().get();
        if(entrepriseOptional.isPresent()) {
            Entreprise entreprise = entrepriseOptional.get();
            if(result == null || (int) result <= 0){
                log.error("Object {} has not been added to database with result of {}",
                        entreprise,
                        result);
            }else {
                log.info("Object {} has been added successfully",
                        Arrays.stream(joinPoint.getArgs()).findFirst().get());
            }
        }

    }
}
