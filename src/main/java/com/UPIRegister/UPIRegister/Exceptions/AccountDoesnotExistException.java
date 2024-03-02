package com.UPIRegister.UPIRegister.Exceptions;

public class AccountDoesnotExistException extends RuntimeException{
    public AccountDoesnotExistException(String message){
        super(message);
    }
}
