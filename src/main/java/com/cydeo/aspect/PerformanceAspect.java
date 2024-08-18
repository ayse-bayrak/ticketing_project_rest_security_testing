package com.cydeo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


// in the interview when she display the this class interviewer  really impressed so popular topic,
//so you can display this PerformanceAspect Class and custom annotation
@Aspect
@Component
@Slf4j
public class PerformanceAspect {
    @Pointcut("@annotation(com.cydeo.annotation.ExecutionTime)")
    public void executionTimePC() {}

    /*
    we add in UserController class
    @ExecutionTime
    @GetMapping("/{username}")
     */

    @Around("executionTimePC()")
    public Object aroundAnyExecutionTimeAdvice (ProceedingJoinPoint proceedingJoinPoint) {

        long beforeTime = System.currentTimeMillis(); // give me current time in ml second
        Object result = null;
        log.info("Execution starts:");

        try{
            result = proceedingJoinPoint.proceed();
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }

        long afterTime = System.currentTimeMillis();
        log.info("Time taken to execute: {} ms - Method : {}"
                , (afterTime-beforeTime), proceedingJoinPoint.getSignature().toShortString());

        return result;

    }
}
