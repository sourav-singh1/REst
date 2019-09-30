package com.yash.demo.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.demo.dao.EmployeeDAO;
import com.yash.demo.exception.MyEmployeeNotFoundException;
import com.yash.demo.model.Employee;
import com.yash.demo.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDAO employeeDAO;

	@Override
	public int addEmployee(Employee employee) {
		employeeDAO.save(employee);
		return 1;
	}

	@Override
	public List<Employee> getAllEmployee() {
		return employeeDAO.findAll();
	}

	@Override
	public int deleteEmployeeByID(Integer employeeID) {
		employeeDAO.findById(employeeID).orElseThrow(()->new MyEmployeeNotFoundException("No such employee with emp id: "+employeeID));
		employeeDAO.deleteById(employeeID);
		return 1;
	}

	@Override
	public int updateEmployeeByID(Employee employee, Integer empID) {
		if (employeeDAO.findById(employee.getEmpId()).isPresent()) {
			employee.setEmpId(employee.getEmpId());
			employeeDAO.save(employee);
			return 1;
		} else
			return 0;
	}

	@Override
	public Employee findByID(Integer empID) throws MyEmployeeNotFoundException {
	 return employeeDAO.findById(empID).orElseThrow(()->new MyEmployeeNotFoundException("No Employee Found with employee id: "+empID));
	}

}
