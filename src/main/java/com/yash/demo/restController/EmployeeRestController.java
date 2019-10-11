package com.yash.demo.restController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/employee")
@Api(value="/employee",description="Employee CRUD Operation",produces ="application/json")
public class EmployeeRestController {

	@Autowired
	private EmployeeService empService;
	
	 @ApiOperation(value="get Employees list",response=Employee.class)
	    @ApiResponses(value={
	    @ApiResponse(code=200,message="Employee Details Retrieved",response=Employee.class),
	   @ApiResponse(code=500,message="Internal Server Error"),
	   @ApiResponse(code=404,message="Employee not found")
	    })

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
	 
	 @ApiOperation(value="get Employee Object",response=Employee.class)
	    @ApiResponses(value={
	    @ApiResponse(code=200,message="Employee Details Retrieved",response=Employee.class),
	   @ApiResponse(code=500,message="Internal Server Error"),
	   @ApiResponse(code=404,message="Employee not found")
	    })
	@GetMapping(path = "/{id}", produces = { "application/xml", "application/json" })
	public Employee findEmployeeByEmpID(@PathVariable String id) {
		Employee employee = empService.findByID(Integer.parseInt(id));
		Link link = ControllerLinkBuilder.linkTo(EmployeeRestController.class).slash(employee.getEmpId()).withSelfRel();
		employee.add(link);
		return employee;
	}
	 
	 @ApiOperation(value="Save Employee to database",response=Employee.class)
	    @ApiResponses(value={
	    @ApiResponse(code=201,message="Employee Details submitted",response=Employee.class),
	   @ApiResponse(code=500,message="Internal Server Error"),
	   @ApiResponse(code=404,message="Employee not found")
	    })
	@PostMapping(consumes = { "application/xml", "application/json" }, produces = { "application/xml",
			"application/json" })
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		
		return new ResponseEntity<Employee>(empService.addEmployee(employee),HttpStatus.CREATED);

	}
	 
	 @ApiOperation(value="Put Employee to database can update if already exist",response=Employee.class)
	    @ApiResponses(value={
	    @ApiResponse(code=200,message="Employee Details submitted",response=Employee.class),
	   @ApiResponse(code=500,message="Internal Server Error"),
	   @ApiResponse(code=404,message="Employee not found")
	    })
	@PutMapping(path = "/{id}", consumes = { "application/xml", "application/json" }, produces = { "application/xml",
			"application/json" })
	public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee, @PathVariable("id") String id)
			throws MyEmployeeNotFoundException {
		employee.setEmpId(Integer.parseInt(id));
		Integer status = empService.updateEmployeeByID(employee, Integer.parseInt(id));
		if (status != 1) {
			throw new MyEmployeeNotFoundException("no data associate with employee id :" + id);
		} else
			return new ResponseEntity<Employee>(employee, HttpStatus.OK);

	}
	 
	 @ApiOperation(value="Delete Employee by ID",response=Employee.class)
	    @ApiResponses(value={
	    @ApiResponse(code=204,message="Employee Details Deleted",response=Employee.class),
	   @ApiResponse(code=500,message="Internal Server Error"),
	   @ApiResponse(code=404,message="Employee not found")
	    })
	@DeleteMapping(value = "/{id}", consumes = { "application/xml", "application/json" }, produces = {
			"application/xml", "application/json" })
	public ResponseEntity<HttpStatus> deleteEmployeeByID(@PathVariable String id) throws MyEmployeeNotFoundException {
		Integer empID = Integer.parseInt(id);
		empService.deleteEmployeeByID(empID);
		return new ResponseEntity("Deleted Successfully", HttpStatus.NO_CONTENT);
	}
}
