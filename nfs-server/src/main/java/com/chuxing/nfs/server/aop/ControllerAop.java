package com.chuxing.nfs.server.aop;

import com.chuxing.nfs.server.common.response.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @date 2022/6/27 19:58
 * @author huangchenguang
 * @desc controller aop
 */
@Aspect
@Component
public class ControllerAop {

    @Pointcut("execution(* com.chuxing.nfs.server.controller.*.*(..))")
    public void pointcut() {
    }

    /**
     * @date 2022/6/27 19:59
     * @author huangchenguang
     * @desc cache and throw error msg
     */
    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) {
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            return Result.fail(null, e.getMessage());
        }
    }

}
