package com.apirestdocker.course.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnsupportedMathOperationMath extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UnsupportedMathOperationMath(String msg) {
		super(msg);
	}
	
}
