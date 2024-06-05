package com.gbLisboa.NorlimpApplication.domain.exception;

public class PaymentException extends RuntimeException{
    public PaymentException(String message){
        super(message);
    }
}
