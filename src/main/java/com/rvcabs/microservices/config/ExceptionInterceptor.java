package com.rvcabs.microservices.config;

//import java.net.http.HttpHeaders;
import java.nio.file.AccessDeniedException;
import java.util.Date;

import org.springframework.http.HttpHeaders;

//import org.apache.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rvcabs.microservices.constants.Constants;
import com.rvcabs.microservices.exception.BadRequestException;
import com.rvcabs.microservices.exception.BaseResponseDto;
import com.rvcabs.microservices.exception.ErrorDetails;
import com.rvcabs.microservices.exception.InternalServerException;
import com.rvcabs.microservices.exception.NoDataFoundException;
import com.rvcabs.microservices.exception.UnAuthorizedException;
import com.rvcabs.microservices.exception.UnProcessibleException;
import com.rvcabs.microservices.utilities.Utilities;

@ControllerAdvice
public class ExceptionInterceptor extends ResponseEntityExceptionHandler {

	/*
	 * @ExceptionHandler({ ConflictException.class }) public final
	 * ResponseEntity<Object> handleConflictException(ConflictException ex) { final
	 * HttpHeaders headers = Utilities.getDefaultHttpHeaders();
	 * headers.add(Constants.MESSAGE, ex.getMessage()); return new
	 * ResponseEntity(new BaseResponseDto(HttpStatus.CONFLICT.value(),
	 * ex.getMessage()), headers, HttpStatus.CONFLICT);
	 * 
	 * }
	 */

	@ExceptionHandler({ UnProcessibleException.class })
	public final ResponseEntity<Object> handleUnProcessibleException(UnProcessibleException ex) {
		final HttpHeaders headers = Utilities.getDefaultHttpHeaders();
		headers.add(Constants.MESSAGE, ex.getMessage());
		return new ResponseEntity(new BaseResponseDto(HttpStatus.UNPROCESSABLE_ENTITY.value(), ex.getMessage()),
				headers, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler({ InternalServerException.class })
	public final ResponseEntity<Object> handleInternalServerException(InternalServerException ex) {
		final HttpHeaders headers = Utilities.getDefaultHttpHeaders();
		headers.add(Constants.MESSAGE, ex.getMessage());
		return new ResponseEntity(new BaseResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage()),
				headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ NoDataFoundException.class })
	public final ResponseEntity<Object> handleInternalServerException(NoDataFoundException ex) {
		final HttpHeaders headers = Utilities.getDefaultHttpHeaders();
		headers.add(Constants.MESSAGE, ex.getMessage());
		return new ResponseEntity(new BaseResponseDto(HttpStatus.NO_CONTENT.value(), ex.getMessage()), headers,
				HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(BadRequestException.class)
	public final ResponseEntity<ErrorDetails> handleBadRequestException(BadRequestException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getErrors(), HttpStatus.BAD_REQUEST.value(),
				request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ UnAuthorizedException.class })
	public final ResponseEntity<Object> handleUnAuthorizedException(UnAuthorizedException ex) {
		final HttpHeaders headers = Utilities.getDefaultHttpHeaders();
		headers.add(Constants.MESSAGE, ex.getMessage());
		return new ResponseEntity(new BaseResponseDto(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()), headers,
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({ AccessDeniedException.class })
	public final ResponseEntity<Object> handleUnAuthorizedException(AccessDeniedException ex) {
		final HttpHeaders headers = Utilities.getDefaultHttpHeaders();
		headers.add(Constants.MESSAGE, ex.getMessage());
		return new ResponseEntity(new BaseResponseDto(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()), headers,
				HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({ Exception.class })
	public final ResponseEntity<Object> handleInternalServerException(Exception ex) {
		final HttpHeaders headers = Utilities.getDefaultHttpHeaders();
		headers.add(Constants.MESSAGE, ex.getMessage());
		return new ResponseEntity(new BaseResponseDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), headers,
				HttpStatus.BAD_REQUEST);
	}
}