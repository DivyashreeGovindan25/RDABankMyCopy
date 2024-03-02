package com.UPIRegister.UPIRegister.Exceptions;

public class AlreadyRegisteredForUPI extends RuntimeException{
    public AlreadyRegisteredForUPI(String message){
        super(message);
    }
}
