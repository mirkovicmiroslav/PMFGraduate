package com.pmfgraduate.exception;

import org.springframework.http.HttpStatus;

public class PmfGraduateException extends RuntimeException {

	private static final long serialVersionUID = 8015647556248899491L;

	private HttpStatus statusCode;
	private String message;

	public PmfGraduateException(HttpStatus statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
