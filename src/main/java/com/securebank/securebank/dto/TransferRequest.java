package com.securebank.securebank.dto;

import lombok.Data;

@Data
public class TransferRequest {

    private String fromAccount;

    private String toAccount;

    private Double amount;

}