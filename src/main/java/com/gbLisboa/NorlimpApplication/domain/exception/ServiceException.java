package com.gbLisboa.NorlimpApplication.domain.exception;

public class ServiceException extends RuntimeException{
    public ServiceException(String message){
        super(message);
    }
}
