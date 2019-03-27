package com.aop.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.aop.employee.model.Employee;
import com.aop.employee.service.EmployeeManager;

@SpringBootApplication
@EnableAspectJAutoProxy
public class AopEmployeeDemoApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(AopEmployeeDemoApplication.class, args);
		
        EmployeeManager manager = (EmployeeManager) context.getBean(EmployeeManager.class);
        
        manager.getEmployeeById(1);
        manager.createEmployee(new Employee());
	}

}
