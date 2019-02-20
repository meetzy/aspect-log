package com.xfmeet.core.log.aspect;

import com.xfmeet.core.log.annotation.CommonLog;
import com.xfmeet.core.log.common.CommonLogUtils;
import com.xfmeet.core.log.common.LogType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;

import java.lang.reflect.Method;

/**
 * @author meetzy
 */
@Aspect
@Order(Integer.MIN_VALUE)
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "core.log")
public class ServiceLogAspect {
    
    /**
     * all log level
     */
    private String level = "info";
    
    public String getLevel() {
        return level;
    }
    
    public void setLevel(String level) {
        this.level = level;
    }
    
    @Pointcut("execution(public * *..service.impl.*Impl.*(..))")
    public void serviceLog() {
    }
    
    @Around("serviceLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CommonLog log = method.getAnnotation(CommonLog.class);
        if (log != null) {
            return joinPoint.proceed();
        }
        String className = CommonLogUtils.getClassName(joinPoint.getTarget().getClass(), LogType.SIMPLE);
        String methodName = method.getName();
        CommonLogUtils.log(level, CommonLogUtils.getLogString(className, methodName, joinPoint.getArgs()));
        Object obj = joinPoint.proceed();
        if (method.getReturnType() != Void.TYPE) {
            CommonLogUtils.log(level, CommonLogUtils.getLogString(className, methodName, obj));
        }
        return obj;
    }
    
}
