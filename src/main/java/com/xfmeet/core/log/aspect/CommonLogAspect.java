package com.xfmeet.core.log.aspect;

import com.alibaba.fastjson.JSON;
import com.xfmeet.core.log.annotation.CommonLog;
import com.xfmeet.core.log.common.CommonLogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author meetzy
 */
@Aspect
@Component
@Order(2147483646)
public class CommonLogAspect {


    @Pointcut("@annotation(com.xfmeet.core.log.annotation.CommonLog)")
    public void serviceLog() {
    }

    @Around("serviceLog()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String className = joinPoint.getTarget().getClass().getCanonicalName();
        String methodName = joinPoint.getSignature().getName();
        String level = getLogLevel(joinPoint);
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

    private String getLogLevel(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CommonLog log = method.getAnnotation(CommonLog.class);
        return log.value();
    }
}
