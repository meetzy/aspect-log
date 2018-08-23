package com.xfmeet.core.log.aspect;

import com.alibaba.fastjson.JSON;
import com.xfmeet.core.log.annotation.CommonLog;
import com.xfmeet.core.log.common.CommonLogUtils;
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
 * @author meet
 */
@Aspect
@Order
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "core.log")
public class ServiceLogAspect {

    /**
     * 日志拦截级别
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
        String className = joinPoint.getTarget().getClass().getCanonicalName();
        String methodName = joinPoint.getSignature().getName();
        CommonLogUtils.log(level, getLogString(className, methodName, joinPoint.getArgs()));
        Object obj = joinPoint.proceed();
        CommonLogUtils.log(level, getLogString(className, methodName, obj));
        return obj;
    }

    private String getLogString(String className, String methodName, Object arg) {
        return String.format("className:{%s}", className) +
                String.format("-->methodName:{%s}", methodName) +
                String.format("-->params:{%s}", JSON.toJSONString(arg));
    }

}
