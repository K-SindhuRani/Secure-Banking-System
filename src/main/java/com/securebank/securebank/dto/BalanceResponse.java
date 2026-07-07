package com.securebank.securebank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceResponse {

    private String accountNumber;
    private Double balance;
}