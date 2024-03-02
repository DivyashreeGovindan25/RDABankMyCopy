package com.UPIRegister.UPIRegister.Exceptions;

public class InvalidAccountNumberException extends RuntimeException{
    public InvalidAccountNumberException(String message){
        super(message);
    }
}
