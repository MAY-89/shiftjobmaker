package com.hanbal.shiftcracker.api.aop;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AopConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(AopConfig.class);

    
    @Before("execution(* com.hanbal.shiftcracker.api.DBRepository*(..))")
    public void doSomethingBefore(JoinPoint jp) {
        logger.info("------------------------------");
		logger.info("---------- START LOG ---------");
		logger.info("target : " + jp.getTarget());
		logger.info("type : " + jp.getKind()); // method
		logger.info("parameter : " + Arrays.toString(jp.getArgs()));
		logger.info("name : " + jp.getSignature().getName());
		logger.info("LOG start time : " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
		logger.info("----------- END LOG ----------");		
		logger.info("------------------------------");
    }

    @After("execution(* com.hanbal.shiftcracker.api.DBRepository*(..))")
	public void endLog(JoinPoint jp) throws RuntimeException {
		logger.info("------------------------------");
		logger.info("---------- START AFTER LOG ---------");
		logger.info("target : " + jp.getTarget());
		logger.info("type : " + jp.getKind()); // method
		logger.info("parameter : " + Arrays.toString(jp.getArgs()));
		logger.info("name : " + jp.getSignature().getName());
		logger.info("LOG END time : " + new SimpleDateFormat("HH:mm:ss").format(new Date()));
		logger.info("----------- END AFTER LOG ----------");		
		logger.info("------------------------------");
	}
	
	@AfterReturning(
		pointcut="execution(!Void com.hanbal.shiftcracker.api.DBRepository.*(..))",
		returning="returnValue"
	)
	public void writeSuccessLog(JoinPoint jp, Object returnValue) throws RuntimeException{
		logger.info("------ START AfterRetuning LOG ------");
		logger.info("name : " + jp.getSignature().getName());
		logger.info("returnValue : " + returnValue);
		logger.info("------ END AfterRetuning LOG ------");
	}
	
	@AfterThrowing(
	value="execution(* com.hanbal.shiftcracker.api.DBRepository*(..))",
	throwing="exception"
	)
	public void endThrowingLog(JoinPoint jp, Exception exception) throws Exception {
		logger.info("------ START AfterThrowing LOG");
		logger.info("name : " + jp.getSignature().getName());
		logger.info("exception : " + exception.getMessage());
		logger.info("------ END AfterThrowing LOG");
	}

}
