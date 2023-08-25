package ru.sevmash.timesheetaccounting.aspesct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect {
    Logger logger = LogManager.getLogger(getClass());

    @Around(value = "@annotation(ToLog)")
    public Object logToConsole(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        logger.info("---->>>> ");
        Object returnedValue = proceedingJoinPoint.proceed();
        logger.info("-------- return := "+returnedValue);
        logger.info("<<<<---- ");

        return returnedValue;
    }
}

