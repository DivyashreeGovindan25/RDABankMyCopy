package com.UPIRegister.UPIRegister.DTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonalDetailResposneDTO {
    Long ACCOUNT_NO;
    String EMAIL;
    Long PHONE;
}
