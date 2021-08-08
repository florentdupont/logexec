package com.example.demo;


import com.example.demo.log.Level;
import com.example.demo.log.LogExec;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.EnableLoadTimeWeaving;

import java.lang.reflect.Method;

import static org.springframework.context.annotation.EnableLoadTimeWeaving.AspectJWeaving.ENABLED;


@Configuration
@Aspect
@EnableAspectJAutoProxy
@EnableLoadTimeWeaving(aspectjWeaving = ENABLED)
public class LogExecConfig {


    @Around("@annotation(com.example.demo.log.DebugExec)")
    public Object aroundDebugExec(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Logger log = LoggerFactory.getLogger(method.getDeclaringClass());
        log.debug("{}() - start",  method.getName());

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed(joinPoint.getArgs());
        long timeTaken = System.currentTimeMillis() - startTime;

        log.debug("{}() - executed in {} ms",  method.getName(), timeTaken);
        return result;
    }

    @Around(value = "@annotation(logExec)", argNames= "joinPoint,logExec")
    public Object aroundLogExecWith(ProceedingJoinPoint joinPoint, LogExec logExec) throws Throwable {

        // https://stackoverflow.com/questions/62298498/spring-aop-around-access-the-value-of-annotation
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Logger log = LoggerFactory.getLogger(method.getDeclaringClass());

        doLog(log, logExec.value(), "{}() - start", method.getName() );

        Object result;
        if(logExec.timed()) {
            long startTime = System.currentTimeMillis();
            result = joinPoint.proceed(joinPoint.getArgs());
            long timeTaken = System.currentTimeMillis() - startTime;
            doLog(log, logExec.value(), "{}() - executed in {} ms", method.getName(), timeTaken );
        } else {
            result = joinPoint.proceed(joinPoint.getArgs());
            doLog(log, logExec.value(), "{}() - end", method.getName() );
        }

        return result;
    }

    public void doLog(Logger logger, Level level, String msg, Object ... arguments) {
        switch(level) {
            case TRACE: logger.trace(msg, arguments);
                break;
            case DEBUG: logger.debug(msg, arguments);
                        break;
            case INFO: logger.info(msg, arguments);
                        break;
            case WARN: logger.warn(msg, arguments);
                        break;
            case ERROR: logger.error(msg, arguments);
                        break;
        }
    }
}
