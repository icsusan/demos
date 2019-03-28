package com.aop.employee.service;

import java.util.List;

import com.aop.employee.model.Employee;

public interface EmployeeManager {

	public Employee getEmployeeById(Integer employeeId);

	public List<Employee> getAllEmployee();

	public void createEmployee(Employee employee) throws Exception;

	public void deleteEmployee(Integer employeeId);

}
