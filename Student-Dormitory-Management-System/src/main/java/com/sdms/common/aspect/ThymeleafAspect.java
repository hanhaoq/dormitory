package com.sdms.common.aspect;

import com.sdms.service.SessionService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Aspect
public class ThymeleafAspect {

    @Resource
    private SessionService sessionService;
    //在任务管理应用程序中
    // 会话未处理计数可以表示用户待办任务的数量。
    // 在消息通知应用程序中，会话未处理计数可以表示用户尚未查看的未读通知数量。
    @Around("execution(public String com.sdms.controller.*Controller.to*(..))")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object proceed = joinPoint.proceed();
        sessionService.refreshNoHandleCount();
        return proceed;
    }
// @Around 注解标记了一个环绕通知（around advice）。
// 它定义了切点表达式 execution(public String com.sdms.controller.*Controller.to*(..))，
// 表示拦截所有以 to 开头并返回类型为 String 的公共方法。
}
