package com.yash.demo.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.yash.demo.dao.EmployeeDAO;
import com.yash.demo.exception.MyEmployeeNotFoundException;
import com.yash.demo.model.Employee;
import com.yash.demo.service.EmployeeService;
import com.yash.demo.serviceImpl.EmployeeServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@Mock
	private EmployeeDAO employeeDAO;

	@Test
	public void shouldAddEmployeeWhenAddEmployeeIsInvoked() {

		Employee employee = new Employee();
		employee.setEmpId(100);
		employee.setEmpName("Rakesh");
		employee.setEmpDept("Accounts");
		employee.setEmpSalary(87654);

		when(employeeDAO.save(employee)).thenReturn(employee);
		employeeService.addEmployee(employee);
		verify(employeeDAO).save(employee);
	}

	@Test
	public void shouldFetchAllEmployeeWhenGetAllEmployeeIsInvoked() {

		List<Employee> employeeList = new ArrayList<Employee>();

		Employee emp1 = new Employee(123, "Shankar", "Software", 120000);
		Employee emp2 = new Employee(124, "Shuvan", "Software", 110000);
		Employee emp3 = new Employee(125, "keshri", "Software", 100000);

		employeeList.add(emp1);
		employeeList.add(emp2);
		employeeList.add(emp3);

		when(employeeDAO.findAll()).thenReturn(employeeList);
		List<Employee> actualEmployeeList = employeeService.getAllEmployee();
		verify(employeeDAO).findAll();
		assertEquals(employeeList, actualEmployeeList);
	}

	@Test
	public void shouldDeleteEmployeeByIdWhenValidEmployeeIdIsGiven() {

		Employee emp1 = new Employee(123, "Shankar", "Software", 120000);

		when(employeeDAO.findById(123)).thenReturn(Optional.of(emp1));
		doNothing().when(employeeDAO).deleteById(123);
		employeeService.deleteEmployeeByID(123);
		verify(employeeDAO).deleteById(123);
	}
	
	@Test(expected = MyEmployeeNotFoundException.class)
	public void shouldThrowExceptionWhenInvalidEmployeeIdIsGiven() {
		
		when(employeeDAO.findById(123)).thenThrow(new MyEmployeeNotFoundException("No such employee with emp id: 123"));
		employeeService.deleteEmployeeByID(123);
	}
	
		@Test
		public void shouldUpdateEmployeeWhenValidEmployeeIdIsGiven() {
			
			Employee emp1 = new Employee(123, "Shankar", "Software", 120000);
			Employee emp2 = new Employee(null, "Shuvan", "Software", 110000);
			
			when(employeeDAO.findById(emp1.getEmpId())).thenReturn(Optional.of(emp1));
			emp2.setEmpId(emp1.getEmpId());
			when(employeeDAO.save(emp2)).thenReturn(emp2);
			int actual = employeeService.updateEmployeeByID(emp2, 123);
			verify(employeeDAO).save(emp2);
			assertEquals(1, actual);
			
		}
		@Test
		public void shouldNotUpdateEmployeeWhenAnInvalidEmployeeIdIsGiven() {
			
			Employee emp1 = new Employee(123, "Shankar", "Software", 120000);
			Employee emp2 = new Employee(null, "Shuvan", "Software", 110000);
			int expected = 0;
			when(employeeDAO.findById(12)).thenReturn(Optional.ofNullable(null));
			emp2.setEmpId(12);
			int actual = employeeService.updateEmployeeByID(emp2, 12);
			assertEquals(expected, actual);
			
		}
		
		@Test
		public void shouldReturnEmployeeWhenValidEmployeeIsGiven() {
			
			Employee emp1 = new Employee(123, "Shankar", "Software", 120000);
			
			when(employeeDAO.findById(123)).thenReturn(Optional.of(emp1));
			Employee actual = employeeService.findByID(123);
			verify(employeeDAO).findById(123);
			assertEquals(emp1, actual);
		}
		
		@Test(expected = MyEmployeeNotFoundException.class)
		public void shouldThrowExceptionWhenInvalidEmployeeIsGiven() {
			
			when(employeeDAO.findById(1021)).thenThrow(new MyEmployeeNotFoundException("No Such Employee"));
			employeeService.findByID(1021);
		}
		
		@Test(expected = MyEmployeeNotFoundException.class)
		public void shouldReturnNullAndExceptionWhenInvalidEmployeeIsGiven() {
			
			when(employeeDAO.findById(1021)).thenReturn(Optional.ofNullable(null));
		employeeService.findByID(1021);
		}
		
		@Test(expected = MyEmployeeNotFoundException.class)
		public void shouldReturnNullAndExceptionWhenInvalidEmployeeIsGivenWhileCallingDeleteEmployeeByID() {
			
			when(employeeDAO.findById(1021)).thenReturn(Optional.ofNullable(null));
			employeeService.deleteEmployeeByID(1021);
		}
	

}
