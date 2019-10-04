package com.yash.demo.model;

public class ErrorResponse {

	private Integer status;
	private String errorMsg;

	public ErrorResponse(Integer status, String errorMsg) {
		super();
		this.status = status;
		this.errorMsg = errorMsg;
	}

	public Integer getStatus() {
		return status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}
