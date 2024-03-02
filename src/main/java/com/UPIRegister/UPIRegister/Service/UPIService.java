package com.UPIRegister.UPIRegister.Service;

import com.UPIRegister.UPIRegister.DTO.PersonalDetailResposneDTO;
import com.UPIRegister.UPIRegister.DTO.UPIRegisterDTO;
import com.UPIRegister.UPIRegister.Models.UPIDetails;
import com.UPIRegister.UPIRegister.Repository.UPIRepository;
import com.UPIRegister.UPIRegister.Utils.CommunicationInfo;
import com.UPIRegister.UPIRegister.Utils.UPIRegiterValidations;
import com.UPIRegister.UPIRegister.Utils.URIS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



import static com.UPIRegister.UPIRegister.Utils.UPIIdType.SAMEASBANKEMAIL;
import static com.UPIRegister.UPIRegister.Utils.UPILogger.upiLogger;

@Service
public class UPIService {
    @Autowired
    CommunicationInfo communicationInfo;
    @Autowired
    UPIRepository upr;
    public String registerUPI(UPIRegisterDTO upiRegisterDTO) throws Exception{
        //Write Logic to check if user has account in bank
        //Fetching Account details
        String uri = URIS.getPersonalDetailsURL + "?accountNumber=" + upiRegisterDTO.getAccountNo();
        ResponseEntity<PersonalDetailResposneDTO> responsePersonalDetail = communicationInfo.getRestTemplate().exchange(uri,
                HttpMethod.GET,communicationInfo.getHttpEntityForGet(),PersonalDetailResposneDTO.class);
        //Account number/Account validation
        try{
            UPIRegiterValidations.UPIRegisterAccountNumberValidation(upiRegisterDTO,
                    upr.findById(upiRegisterDTO.getAccountNo()).orElse(null),responsePersonalDetail.getBody());
        }finally {
            System.out.println("Account number validation");
        }
        upiLogger.info("Account/Account number validation passed");
        //Valid UPI ID check
        if(upiRegisterDTO.getUpiIdType().equals(SAMEASBANKEMAIL)){
            upiRegisterDTO.setUpiId(responsePersonalDetail.getBody().getEMAIL());
        }
        String UPI_ID = UPIRegiterValidations.generateUPIId(upiRegisterDTO.getUpiId());
        try {
            UPIRegiterValidations.UPIRegisterUPIIdValidation(upiRegisterDTO,upr.getUPIWithTheProvidedUPIId(UPI_ID));
            upiRegisterDTO.setUpiId(UPI_ID);
        }finally {
            System.out.println("UPI Id validation");
        }
        upiLogger.info("UPI Id validation passed");
        //UPI PIN validation
        try {
            UPIRegiterValidations.UPIRegisterUPIPinValidation(upiRegisterDTO);
        }finally {
            System.out.println("UPI Pin validation");
        }
        upiLogger.info("UPI Pin validation passed");
        UPIDetails upiDetails = new UPIDetails();
        upiDetails.setAccountNo(upiRegisterDTO.getAccountNo());
        upiDetails.setUpiId(upiRegisterDTO.getUpiId());
        upiDetails.setUpiPin(upiRegisterDTO.getUpiPin());
        upr.save(upiDetails);
        upiLogger.info("Details saved into database");
        return String.format("UPI register successfull for account number %d ",upiDetails.getAccountNo());
    }
    public UPIDetails getUPIDetails(Long accountNumber){
        return upr.findById(accountNumber).orElse(null);
    }
}
