package com.aop.employee.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.aop.employee.model.Employee;
import com.aop.employee.service.EmployeeManager;

@Service
public class EmployeeManagerImpl implements EmployeeManager {

	public Employee getEmployeeById(Integer employeeId) {
		System.out.println("Method getEmployeeById() called");
		return new Employee();
	}

	public List<Employee> getAllEmployee() {
		System.out.println("Method getAllEmployee() called");
		return new ArrayList<Employee>();
	}

	public void createEmployee(Employee employee) throws Exception {
		System.out.println("Method createEmployee() called");
		throw new Exception("Excepcion forzada....................");
	}

	public void deleteEmployee(Integer employeeId) {
		System.out.println("Method deleteEmployee() called");
	}

	public void updateEmployee(Employee employee) {
		System.out.println("Method updateEmployee() called");
	}

}
