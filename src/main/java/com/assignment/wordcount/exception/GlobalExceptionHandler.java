package com.assignment.wordcount.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler { 


	@ExceptionHandler(Throwable.class)
	@ResponseBody
	ResponseEntity<Object> handleBaseExceptionException(HttpServletRequest req, Throwable ex) {
		 if(ex instanceof BaseException) {
			 BaseException e = (BaseException)ex;
	            return new ResponseEntity<Object>(new ErrorResponse(e.getStatus(),e.getErrorMessage()),e.getStatus());
	        } else {
	            return new ResponseEntity<Object>(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	        }
		
	}


}
