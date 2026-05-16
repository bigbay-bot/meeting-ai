package com.meeting.ai.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.meeting.ai.controller.*.*(..))")
    public void controllerPointcut() {}

    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String methodName = point.getSignature().getName();
        long start = System.currentTimeMillis();
        log.info("[请求开始] 方法: {}", methodName);
        Object result = point.proceed();
        long cost = System.currentTimeMillis() - start;
        log.info("[请求结束] 方法: {}, 耗时: {}ms", methodName, cost);
        return result;
    }
}
