package com.UPIRegister.UPIRegister.DTO;

import com.UPIRegister.UPIRegister.Utils.UPIIdType;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UPIRegisterDTO {
    Long accountNo;
    String upiId;
    Integer upiPin;
    Integer confirmUpiPin;
    UPIIdType upiIdType;
}
