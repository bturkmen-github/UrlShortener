package com.bturkmen.urlShortener.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors()
			.forEach(x->{
				String fieldName = ((FieldError) x).getField();
				String errorMessage = x.getDefaultMessage();
				errors.put(fieldName, errorMessage);
			});
		
		return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ShortUrlNotFoundException.class)
	public ResponseEntity<?> shortUrlNotFoundException(ShortUrlNotFoundException exception){
		Map<String,String> error = new HashMap<>();
		error.put("error",exception.getMessage());
		return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CodeAlreadyExists.class)
	public ResponseEntity<?> codeAlreadyExists(CodeAlreadyExists exception){
		Map<String,String> error = new HashMap<>();
		error.put("error",exception.getMessage());
		return new ResponseEntity<>(error,HttpStatus.CONFLICT);
	}
	
}
