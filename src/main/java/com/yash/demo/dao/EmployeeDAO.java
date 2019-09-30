package com.yash.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yash.demo.model.Employee;

@Repository
public interface EmployeeDAO extends JpaRepository<Employee, Integer>{
	

}
