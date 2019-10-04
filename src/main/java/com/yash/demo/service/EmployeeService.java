package com.yash.demo.service;

import java.util.List;

import com.yash.demo.model.Employee;

public interface EmployeeService {
	
	public Employee addEmployee(Employee employee);
	public List<Employee> getAllEmployee();
	public void deleteEmployeeByID(Integer empID);
	public int updateEmployeeByID(Employee employee, Integer empID);
	public Employee findByID(Integer empID);

}
