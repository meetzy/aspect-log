package com.xfmeet.core.log.annotation;

import com.xfmeet.core.log.common.LogType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author meetzy
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommonLog {
    /**
     * log level
     *
     * @return  level
     */
    String value() default "INFO";
    
    String  type() default  LogType.SIMPLE;
}
