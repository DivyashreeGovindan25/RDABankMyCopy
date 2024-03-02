package com.UPIRegister.UPIRegister.Utils;

import com.UPIRegister.UPIRegister.DTO.UPIRegisterDTO;
import com.UPIRegister.UPIRegister.Exceptions.*;
import com.UPIRegister.UPIRegister.Models.UPIDetails;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

import static com.UPIRegister.UPIRegister.Utils.UPIIdType.NEWEMAIL;
import static com.UPIRegister.UPIRegister.Utils.UPILogger.upiLogger;

@Component
public class UPIRegiterValidations {
    private static final String regex = "^[a-zA-Z0-9+-_&*]+(?:\\.[a-zA-Z0-9+-_&*]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(regex);

    public static void UPIRegisterAccountNumberValidation(UPIRegisterDTO upiRegisterDTO, UPIDetails upiDetails, Object accountDetails){
        Long accountNumber = upiRegisterDTO.getAccountNo();
        String accountNumberString = String.valueOf(accountNumber);
        if (accountNumberString.length() != 11) {
            upiLogger.info("Encountered account length exception");
            throw new InvalidAccountNumberException(String.format("Kindly enter valid 11 digit account number"));
        }
        if(accountDetails == null){
            upiLogger.info("Encountered account doesn't exist exception");
            throw new AccountDoesnotExistException(String.format("No account exist with account number %d ",upiRegisterDTO.getAccountNo()));
        }
        if(upiDetails != null){
            upiLogger.info("Encountered account already exist in UPI exception");
            throw new AlreadyRegisteredForUPI(String.format("Account number %d already registered for UPI",upiRegisterDTO.getAccountNo()));
        }
    }
    public static void UPIRegisterUPIPinValidation(UPIRegisterDTO upiRegisterDTO){
        String pinString = String.valueOf(upiRegisterDTO.getUpiPin());
        if(pinString.length() != 4){
            upiLogger.info("Encountered UPI Pin length exception");
            throw new InvalidUPIPinException(String.format("Kindly enter valid 4 digit UPI"));
        }
        if(!Objects.equals(upiRegisterDTO.getUpiPin(), upiRegisterDTO.getConfirmUpiPin())){
            upiLogger.info("Encountered UPI pin and confirm pin exception");
            throw new InvalidUPIPinException(String.format("UPI Pin doesn't match with confirm UPI"));
        }
    }
    public static void UPIRegisterUPIIdValidation(UPIRegisterDTO upiRegisterDTO,UPIDetails upiDetails){
        if(!isValidEmail(upiRegisterDTO.getUpiId())){
            upiLogger.info("Encountered invalid email exception");
            throw new InvalidEmailException(String.format("The provided UPI Id is invalid"));
        }
        if(upiRegisterDTO.getUpiIdType() == NEWEMAIL && upiDetails != null){
            upiLogger.info("Encountered UPI ID already exist exception");
            throw new InvalidEmailException(String.format("A different account is already registered with the UPI Id %s",upiRegisterDTO.getUpiId()));
        }
    }
    public static boolean isValidEmail(String email){
        return pattern.matcher(email).matches();
    }
    public static String generateUPIId(String email){
        int idx = email.indexOf("@");
        String userName = email.substring(0,idx+1);
        userName += "rdabank";
        upiLogger.info("UPI Id generated with bank name as extension");
        return userName;
    }
}
