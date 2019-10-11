package com.yash.demo.aop.test;

import static org.mockito.Mockito.when;

import org.aspectj.lang.JoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import com.yash.demo.aop.LogService;
import com.yash.demo.dao.EmployeeDAO;
import com.yash.demo.exception.MyEmployeeNotFoundException;
import com.yash.demo.model.Employee;
import com.yash.demo.service.EmployeeService;
import com.yash.demo.serviceImpl.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class LogServiceTest {
	
	@InjectMocks
	private EmployeeServiceImpl employeeSercive;
	
	@Mock
	private EmployeeDAO employeeDAO;
	

	@Test
	public void shouldPrintLogsForCallingMethodsOfPointExpression() {
		LogService logService = new LogService();
		logService.logMethod();
		Employee employee = new Employee(123, "sourav", "dept", 12345);
		when(employeeDAO.save(employee)).thenReturn(employee);
		
		AspectJProxyFactory factory = new AspectJProxyFactory(employeeSercive);
		factory.addAspect(LogService.class);
		EmployeeService proxy = factory.getProxy();
		proxy.addEmployee(employee);
		
	}
	
	@Test(expected = MyEmployeeNotFoundException.class)
	public void shouldCallLogThrowExceptionWhenAnyMethodHasThrownException() {
		
		Employee employee = new Employee(123, "sourav", "dept", 12345);
		when(employeeDAO.save(employee)).thenThrow(MyEmployeeNotFoundException.class);
		
		AspectJProxyFactory factory = new AspectJProxyFactory(employeeSercive);
		factory.addAspect(LogService.class);
		EmployeeService proxy = factory.getProxy();
		proxy.addEmployee(employee);
	}
}
