package com.UPIRegister.UPIRegister.Models;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "UPIDetails")
public class UPIDetails {
    @Id
    @Column(name = "ACCOUNT_NO",nullable = false,length = 11)
    Long accountNo;
    @Column(name = "UPI_ID",nullable = false,unique = true)
    String upiId;
    @Column(name = "UPI_PIN",nullable = false,length = 4)
    private Integer upiPin;
}
