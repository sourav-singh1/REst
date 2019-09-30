package com.yash.demo.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.demo.exception.MyEmployeeNotFoundException;
import com.yash.demo.model.Employee;
import com.yash.demo.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeRestController {

	@Autowired
	private EmployeeService empService;

	@GetMapping(produces = { "application/xml", "application/json" } )
	public List<Employee> getAllEmployee() {
		
		List<Employee> empList = empService.getAllEmployee();
		for (Employee employee : empList) {
			Link link = ControllerLinkBuilder.linkTo(EmployeeRestController.class).slash(employee.getEmpId())
					.withRel("Self");
			employee.add(link);
		}
		return empList;

	}

	@GetMapping(path = "/{id}", produces = { "application/xml", "application/json" })
	public Employee findEmployeeByEmpID(@PathVariable String id) {
		Employee employee = empService.findByID(Integer.parseInt(id));
		Link link = ControllerLinkBuilder.linkTo(EmployeeRestController.class).slash(employee.getEmpId()).withSelfRel();
		employee.add(link);
		return employee;
	}

	@PostMapping(consumes = { "application/xml", "application/json" }, produces = { "application/xml",
			"application/json" })
	public String saveEmployee(@RequestBody Employee employee) {
		empService.addEmployee(employee);
		return "Successfully updated";

	}

	@PutMapping(path = "/{id}", consumes = { "application/xml", "application/json" }, produces = { "application/xml",
			"application/json" })
	public String updateEmployee(@RequestBody Employee employee, @PathVariable("id") String id)
			throws MyEmployeeNotFoundException {
		employee.setEmpId(Integer.parseInt(id));
		Integer status = empService.updateEmployeeByID(employee, Integer.parseInt(id));
		if (status != 1) {
			throw new MyEmployeeNotFoundException("no data associate with employee id :" + id);
		} else
			return "Employee Data Updated";

	}

	@DeleteMapping(value = "/{id}", consumes = { "application/xml", "application/json" }, produces = {
			"application/xml", "application/json" })
	public String deleteEmployeeByID(@PathVariable String id) throws MyEmployeeNotFoundException {
		Integer empID = Integer.parseInt(id);
		empService.deleteEmployeeByID(empID);
		return "successfully deleted";
	}
}
