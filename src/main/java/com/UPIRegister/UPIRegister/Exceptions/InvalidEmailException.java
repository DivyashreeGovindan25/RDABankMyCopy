package com.UPIRegister.UPIRegister.Exceptions;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException(String msg){
        super(msg);
    }
}
