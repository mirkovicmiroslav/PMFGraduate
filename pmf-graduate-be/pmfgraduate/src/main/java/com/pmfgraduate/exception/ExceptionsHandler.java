package com.pmfgraduate.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	private static final Logger log = LoggerFactory.getLogger(ExceptionsHandler.class);

	@ExceptionHandler(PmfGraduateException.class)
	public final ResponseEntity<?> handleApiError(PmfGraduateException ex) {
		log.error("Error caught: " + ex.getMessage(), ex);
		return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
	}

	@ExceptionHandler(FileSizeLimitExceededException.class)
	public final ResponseEntity<?> uploadedAFileTooLarge(FileSizeLimitExceededException e) {
		System.out.println("b");
		return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MultipartException.class)
	public final ResponseEntity<?> handleFileException(HttpServletRequest request, Throwable ex) {
		System.out.println("a");
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<?> handleAnyException(Exception ex) {
		log.error("Error caught: " + ex.getMessage(), ex);
		System.out.println("c");
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
