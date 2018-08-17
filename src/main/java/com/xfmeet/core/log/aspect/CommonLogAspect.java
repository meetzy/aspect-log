package com.xfmeet.core.log.aspect;

import com.alibaba.fastjson.JSON;
import com.xfmeet.core.log.annotation.CommonLog;
import com.xfmeet.core.log.common.CommonLogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author meet
 */
@Aspect
@Component
public class CommonLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonLogAspect.class);

    @Pointcut("@annotation(com.taoshunda.core.log.annotation.CommonLog)")
    public void serviceLog() {
    }

    @Before("serviceLog()")
    public void doBefore(JoinPoint joinPoint) {
        String level = getLogLevel(joinPoint);
        if (level == null) {
            level = "INFO";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("className:{%s}", joinPoint.getTarget().getClass().getName()))
                .append(String.format("-->methodName:{%s}", joinPoint.getSignature().getName()))
                .append(String.format("-->params:{%s}", Arrays.toString(joinPoint.getArgs())));
        level = level.toUpperCase();
        CommonLogUtils.log(LOGGER, level, stringBuilder);
    }

    @AfterReturning(returning = "object", pointcut = "serviceLog()")
    public void doAfter(JoinPoint joinPoint, Object object) {
        String level = getLogLevel(joinPoint);
        if (level == null) {
            level = "INFO";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("className:{%s}", joinPoint.getTarget().getClass().getName()))
                .append(String.format("-->methodName:{%s}", joinPoint.getSignature().getName()))
                .append(String.format("-->params:{%s}", JSON.toJSONString(object)));
        level = level.toUpperCase();
        CommonLogUtils.log(LOGGER, level, stringBuilder);
    }

    private String getLogLevel(JoinPoint joinPoint) {
        try {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Class<?> clazz = joinPoint.getTarget().getClass();
            Method thisMethod = clazz.getMethod(method.getName(), method.getParameterTypes());
            CommonLog log = thisMethod.getAnnotation(CommonLog.class);
            return log.value();
        } catch (NoSuchMethodException e) {
            LOGGER.warn("Aspect not get method!");
            return null;
        }
    }
}
