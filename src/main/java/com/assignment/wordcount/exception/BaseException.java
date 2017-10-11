package com.assignment.wordcount.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends Exception{
	
	private HttpStatus status;
	private String errorMessage;
	
	public BaseException(HttpStatus httpStatus, String errorMessage) {
		this.status = httpStatus;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

}
