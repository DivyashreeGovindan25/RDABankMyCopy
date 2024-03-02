package com.UPIRegister.UPIRegister.Controller;

import com.UPIRegister.UPIRegister.DTO.GeneralMessageDTO;
import com.UPIRegister.UPIRegister.DTO.UPIRegisterDTO;
import com.UPIRegister.UPIRegister.Exceptions.*;
import com.UPIRegister.UPIRegister.Service.UPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.UPIRegister.UPIRegister.Utils.UPILogger.upiLogger;

@RestController
@RequestMapping("/UPI")
public class UPIController {
    @Autowired
    UPIService ups;
    @PostMapping("/UPIRegister")
    public ResponseEntity registerUPI(@RequestBody UPIRegisterDTO upiRegisterDTO){
        upiLogger.info("In UPI Register controller");
        try{
            String response = ups.registerUPI(upiRegisterDTO);
            upiLogger.info("Successfully registered for UPI");
            return new ResponseEntity<>(new GeneralMessageDTO(response),HttpStatus.CREATED);
        }
        catch (InvalidUPIPinException upiPinException){
            upiLogger.info("Encountered UPI Pin exception");
            return new ResponseEntity<>(new GeneralMessageDTO(upiPinException.getMessage()),HttpStatus.LENGTH_REQUIRED);
        }
        catch (AlreadyRegisteredForUPI alreadyRegisteredForUPI){
            upiLogger.info("Encountered Account already registered for UPI exception");
            return new ResponseEntity<>(new GeneralMessageDTO(alreadyRegisteredForUPI.getMessage()),HttpStatus.CONFLICT);
        }
        catch (AccountDoesnotExistException accountDoesnotExistException){
            upiLogger.info("Encountered account doesn't exist exception");
            return new ResponseEntity<>(new GeneralMessageDTO(accountDoesnotExistException.getMessage()),HttpStatus.NOT_FOUND);
        }
        catch (InvalidEmailException | InvalidAccountNumberException invalidEmailException){
            upiLogger.info("Encountered Invalid Email/Invalid account number exception");
            return new ResponseEntity<>(new GeneralMessageDTO(invalidEmailException.getMessage()),HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        catch (Exception e) {
            upiLogger.info("Encountered unknown exception");
            return new ResponseEntity<>(new GeneralMessageDTO(e.getMessage()),HttpStatus.BAD_GATEWAY);
        }
    }
}
