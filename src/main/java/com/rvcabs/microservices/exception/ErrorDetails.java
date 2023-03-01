package com.rvcabs.microservices.exception;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

    private List<FieldErrorDetails>  errors;
    private Date timestamp;
    private int status;
    private String path;

    public ErrorDetails(Date timestamp,List<FieldErrorDetails> errors,int status, String path) {
        super();
        this.timestamp = timestamp;
        this.errors = errors;
        this.status=status;
        this.path=path;
    }
}
