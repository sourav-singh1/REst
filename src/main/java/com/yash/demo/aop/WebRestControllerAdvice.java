package com.yash.demo.aop;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.MethodNotAllowed;

import com.yash.demo.exception.MyEmployeeNotFoundException;
import com.yash.demo.model.ErrorResponse;

@RestControllerAdvice
public class WebRestControllerAdvice {

	@ExceptionHandler(MyEmployeeNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleNotFoundException(MyEmployeeNotFoundException e) {
		return new ErrorResponse(404, e.getMessage());

	}

}