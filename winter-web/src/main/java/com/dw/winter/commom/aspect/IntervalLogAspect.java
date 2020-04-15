package com.dw.winter.commom.aspect;


import com.dw.winter.annotation.IntervalLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * @author duwen@shein.com
 * @date 2020/3/27
 */
@Slf4j
@Aspect
@Component
public class IntervalLogAspect {

    @Around("@annotation(intervalLog)")
    public Object recordIntervalLog(ProceedingJoinPoint pjp, IntervalLog intervalLog) throws Throwable {
        LocalDateTime beginTime = LocalDateTime.now();
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Object[] args = pjp.getArgs();
        Method method = signature.getMethod();
        String methodName = StringUtils.defaultIfBlank(intervalLog.value(), method.getName());
        String processId = UUID.randomUUID().toString();
        log.info("start to process [{}], params: [{}], beginTime: [{}], processId: [{}]", methodName, args, beginTime, processId);
        Object result = pjp.proceed();
        LocalDateTime endTime = LocalDateTime.now();
        log.info("processId: [{}] finished, endTime: [{}], costTime: [{}] ms", processId, endTime, beginTime.until(endTime, ChronoUnit.MILLIS));
        return result;
    }
}
