package com.securebank.securebank.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.securebank.securebank.dto.BalanceResponse;
import com.securebank.securebank.dto.TransferRequest;
import com.securebank.securebank.entity.Account;
import com.securebank.securebank.entity.User;
import com.securebank.securebank.repository.AccountRepository;
import com.securebank.securebank.repository.UserRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    // Open Bank Account
    public Account createAccount(Long userId, Account account) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()){
           throw new RuntimeException("User Not Found");
    }

        account.setUser(optionalUser.get());
        account.setCreatedDate(LocalDate.now());
        account.setStatus("ACTIVE");
        account.setAccountNumber(generateAccountNumber());

        return accountRepository.save(account);
    }

    // Generate Random Account Number
    private String generateAccountNumber() {
        Random random = new Random();
        return "SB" + (10000000 + random.nextInt(90000000));
    }

    // Get All Accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Get Account By Account Number
    public Account getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
        .orElseThrow(() -> new RuntimeException("Account Not Found"));
    }

    // Balance Enquiry
    public BalanceResponse getBalance(String accountNumber) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account Not Found"));

        return new BalanceResponse(
                account.getAccountNumber(),
                account.getBalance()
        );
    }

    // Deposit Money
    public Account deposit(String accountNumber, Double amount) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElse(null);

        if (account == null) {
             throw new RuntimeException("Account Not Found");
        }

        account.setBalance(account.getBalance() + amount);

        Account updated = accountRepository.save(account);

        transactionService.saveTransaction(
                "DEPOSIT",
                accountNumber,
                accountNumber,
                amount,
                "SUCCESS"
        );

        return updated;
    }

    // Withdraw Money
    public Account withdraw(String accountNumber, Double amount) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElse(null);

        if (account == null) {
            throw new RuntimeException("Account Not Found");
     }

        if (account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance");
        }

        account.setBalance(account.getBalance() - amount);

        Account updated = accountRepository.save(account);

        transactionService.saveTransaction(
                "WITHDRAW",
                accountNumber,
                accountNumber,
                amount,
                "SUCCESS"
        );

        return updated;
    }

    // Transfer Money
    @Transactional
    public String transferMoney(TransferRequest request) {

        Account sender = accountRepository
                .findByAccountNumber(request.getFromAccount())
                .orElseThrow(() -> new RuntimeException("Sender Account Not Found"));

        Account receiver = accountRepository
                .findByAccountNumber(request.getToAccount())
                .orElseThrow(() -> new RuntimeException("Receiver Account Not Found"));

        if (sender.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient Balance");
        }

        sender.setBalance(sender.getBalance() - request.getAmount());

        receiver.setBalance(receiver.getBalance() + request.getAmount());

        accountRepository.save(sender);
        accountRepository.save(receiver);

        transactionService.saveTransaction(
                "TRANSFER",
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount(),
                "SUCCESS"
        );

        return "Transfer Successful";
    }
}