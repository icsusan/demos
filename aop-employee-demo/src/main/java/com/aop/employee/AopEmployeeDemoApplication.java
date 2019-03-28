package com.aop.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.aop.employee.model.Employee;
import com.aop.employee.service.EmployeeManager;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class AopEmployeeDemoApplication {

	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(new Class[] {AopEmployeeDemoApplication.class, AopConfiguration.class}, args);
		
        EmployeeManager manager = (EmployeeManager) context.getBean(EmployeeManager.class);
        
        manager.getEmployeeById(1);
        try {
			manager.createEmployee(new Employee());
		} catch (Exception e) {
			System.out.println("Ajaaaaaaaaaaaaaaaaa");
		}
        
	}

}
