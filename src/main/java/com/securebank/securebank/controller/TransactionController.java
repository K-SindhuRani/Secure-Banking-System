package com.securebank.securebank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securebank.securebank.entity.Transaction;
import com.securebank.securebank.service.TransactionService;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Get All Transactions
    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    // Get Transactions By Account Number
    @GetMapping("/{accountNumber}")
    public List<Transaction> getTransactions(@PathVariable String accountNumber) {
        return transactionService.getTransactions(accountNumber);
    }
}