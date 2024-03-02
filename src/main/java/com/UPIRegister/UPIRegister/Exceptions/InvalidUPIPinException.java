package com.UPIRegister.UPIRegister.Exceptions;

import org.springframework.stereotype.Component;

public class InvalidUPIPinException extends RuntimeException{
    public InvalidUPIPinException(String message){
        super(message);
    }
}
