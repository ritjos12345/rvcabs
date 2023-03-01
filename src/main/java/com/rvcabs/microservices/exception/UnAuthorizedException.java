package com.rvcabs.microservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;
import lombok.Setter;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
@Setter
@Getter
public class UnAuthorizedException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

    public UnAuthorizedException() {
        super("You are Un Authorized");
    }
	
    public UnAuthorizedException(String message) {
        super(message);
    }
	
	public UnAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

}