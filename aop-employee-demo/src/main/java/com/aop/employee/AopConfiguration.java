package com.aop.employee;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.aop.employee.aspect.MyPerformanceMonitorInterceptor;

@Configuration
@EnableAspectJAutoProxy
public class AopConfiguration {
    
//    @Pointcut("execution(* com.aop.employee.service.EmployeeManager.*(..))")
//    public void monitor() { }
    
    @Pointcut("execution(* com.aop.employee.service.EmployeeManager.*(..))")
    public void myMonitor() { }
    
//    @Bean
//    public PerformanceMonitorInterceptor performanceMonitorInterceptor() {
//        return new PerformanceMonitorInterceptor(true);
//    }

//    @Bean
//    public Advisor performanceMonitorAdvisor() {
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression("com.aop.employee.AopConfiguration.monitor()");
//        return new DefaultPointcutAdvisor(pointcut, performanceMonitorInterceptor());
//    }
    
    @Bean
    public MyPerformanceMonitorInterceptor myPerformanceMonitorInterceptor() {
        return new MyPerformanceMonitorInterceptor(true);
    }
    
    @Bean
    public Advisor myPerformanceMonitorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("com.aop.employee.AopConfiguration.myMonitor()");
        return new DefaultPointcutAdvisor(pointcut, myPerformanceMonitorInterceptor());
    }
    
}
