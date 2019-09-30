package com.yash.demo.model;

public class ErrorResponse {
	
	private Integer status;
	private String errorMsg;
	


	public ErrorResponse(Integer status, String errorMsg) {
		super();
		this.status = status;
		this.errorMsg = errorMsg;
	}
	

	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	@Override
	public String toString() {
		return "ErrorResponse [status=" + status + ", errorMsg=" + errorMsg + "]";
	}
	
	

}
