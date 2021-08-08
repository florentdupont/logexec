package com.example.demo.log;


import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * équivalent à LogExec(DEBUG, true) en améliorant la lisibilité du code.
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface DebugExec {}