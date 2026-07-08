package com.securebank.securebank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.securebank.securebank.dto.BalanceResponse;
import com.securebank.securebank.dto.TransferRequest;
import com.securebank.securebank.entity.Account;
import com.securebank.securebank.service.AccountService;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Create Account
    @PostMapping("/user/{userId}")
    public Account createAccount(@PathVariable Long userId,
                                 @RequestBody Account account) {
        return accountService.createAccount(userId, account);
    }

    // Get All Accounts
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Get Account By Account Number
    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        return accountService.getAccountByNumber(accountNumber);
    }

    // Balance Enquiry
    @GetMapping("/balance/{accountNumber}")
    public BalanceResponse getBalance(@PathVariable String accountNumber) {

    Double balance = accountService.getBalance(accountNumber);

    return new BalanceResponse(accountNumber, balance);
}

    // Deposit
    @PutMapping("/deposit/{accountNumber}/{amount}")
    public Account deposit(@PathVariable String accountNumber,
                           @PathVariable Double amount) {
        return accountService.deposit(accountNumber, amount);
    }

    // Withdraw
    @PutMapping("/withdraw/{accountNumber}/{amount}")
    public Account withdraw(@PathVariable String accountNumber,
                            @PathVariable Double amount) {
        return accountService.withdraw(accountNumber, amount);
    }

    // Transfer
    @PostMapping("/transfer")
    public String transferMoney(@RequestBody TransferRequest request) {
        return accountService.transferMoney(request);
    }
}