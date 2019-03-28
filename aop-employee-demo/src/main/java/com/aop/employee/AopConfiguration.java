package com.aop.employee;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.aop.employee.aspect.MonitorPerformanceInterceptor;

@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {
    
    @Pointcut("execution(* com.aop.employee.service.EmployeeManager.*(..))")
    public void myMonitor() { }
    
    @Bean
    public MonitorPerformanceInterceptor myPerformanceMonitorInterceptor() {
        return new MonitorPerformanceInterceptor(true);
    }
    
    @Bean
    public Advisor myPerformanceMonitorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("com.aop.employee.AopConfiguration.myMonitor()");
        return new DefaultPointcutAdvisor(pointcut, myPerformanceMonitorInterceptor());
    }
    
}
