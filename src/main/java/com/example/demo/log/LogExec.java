package com.example.demo.log;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.example.demo.log.Level.INFO;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(METHOD)
@Retention(RUNTIME)
public @interface LogExec {

    Level value() default INFO;

    boolean timed() default false;

}