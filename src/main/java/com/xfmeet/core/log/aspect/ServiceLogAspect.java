package com.xfmeet.core.log.aspect;

import com.alibaba.fastjson.JSON;
import com.xfmeet.core.log.common.CommonLogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Arrays;

/**
 * @author meet
 */
@Aspect
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "core.log")
public class ServiceLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLogAspect.class);
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

    @Before("serviceLog()")
    public void doBefore(JoinPoint joinPoint) {
        level = level.toUpperCase();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("className:{%s}", joinPoint.getTarget().getClass().getName()))
                .append(String.format("-->methodName:{%s}", joinPoint.getSignature().getName()))
                .append(String.format("-->params:{%s}", Arrays.toString(joinPoint.getArgs())));
        CommonLogUtils.log(LOGGER, level, stringBuffer);
    }

    @AfterReturning(returning = "object", pointcut = "serviceLog()")
    public void doAfter(JoinPoint joinPoint, Object object) {
        level = level.toUpperCase();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.format("className:{%s}", joinPoint.getTarget().getClass().getName()))
                .append(String.format("-->methodName:{%s}", joinPoint.getSignature().getName()))
                .append(String.format("-->params:{%s}", JSON.toJSONString(object)));
        CommonLogUtils.log(LOGGER, level, stringBuffer);
    }

}
