package com.yash.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.ResourceSupport;

@Entity
@XmlRootElement
public class Employee extends ResourceSupport {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "empID")
	private Integer empId;

	@Column(name = "empname")
	private String empName;

	@Column(name = "empdept")
	private String empDept;

	@Column(name = "empsalary")
	private Integer empSalary;

	public Integer getEmpId() {
		return empId;
	}

	public void setEmpId(Integer empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getEmpDept() {
		return empDept;
	}

	public void setEmpDept(String empDept) {
		this.empDept = empDept;
	}

	public Integer getEmpSalary() {
		return empSalary;
	}

	public void setEmpSalary(Integer empSalary) {
		this.empSalary = empSalary;
	}
	
	

	public Employee(Integer empId, String empName, String empDept, Integer empSalary) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.empDept = empDept;
		this.empSalary = empSalary;
	}


	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

}
