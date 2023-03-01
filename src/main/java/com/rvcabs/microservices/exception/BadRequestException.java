package com.rvcabs.microservices.exception;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@EqualsAndHashCode(callSuper=false)
@Setter
@Getter
public class BadRequestException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

	private List<FieldErrorDetails>  errors;

    public List<FieldErrorDetails> getErrors() {
		return errors;
	}

	public void setErrors(List<FieldErrorDetails> errors) {
		this.errors = errors;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BadRequestException() {
        super("The request has invalid parameters");
    }
	
    public BadRequestException( List<FieldErrorDetails>  errors) {
        super(errors.toString());
        this.errors=errors;
    }

    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException(Map<String,String> message) {
        super(message.toString());
    }
	public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

}