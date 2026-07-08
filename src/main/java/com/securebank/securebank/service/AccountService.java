package com.securebank.securebank.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.securebank.securebank.dto.TransferRequest;
import com.securebank.securebank.entity.Account;
import com.securebank.securebank.entity.User;
import com.securebank.securebank.repository.AccountRepository;
import com.securebank.securebank.repository.UserRepository;

@Service
public class AccountService {

    private static final Logger logger =
            LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionService transactionService;

    // Open Bank Account
    public Account createAccount(Long userId, Account account) {

        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isEmpty()) {

            logger.error("Account Creation Failed : User {} Not Found", userId);
            throw new RuntimeException("User Not Found");
        }

        account.setUser(optionalUser.get());
        account.setCreatedDate(LocalDate.now());
        account.setStatus("ACTIVE");
        account.setAccountNumber(generateAccountNumber());

        Account savedAccount = accountRepository.save(account);

        logger.info(
                "Account Created Successfully | Account Number : {} | User ID : {}",
                savedAccount.getAccountNumber(),
                userId
        );

        return savedAccount;
    }

    // Generate Random Account Number
    private String generateAccountNumber() {

        Random random = new Random();

        return "SB" + (10000000 + random.nextInt(90000000));
    }

    // Get All Accounts
    public List<Account> getAllAccounts() {

        logger.info("Fetching All Accounts");

        return accountRepository.findAll();
    }

    // Get Account By Account Number
    public Account getAccountByNumber(String accountNumber) {

        logger.info("Fetching Account : {}", accountNumber);

        return accountRepository
                .findByAccountNumber(accountNumber)
                .orElse(null);
    }

    // Deposit
    public Account deposit(String accountNumber, Double amount) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElse(null);

        if (account == null) {

            logger.error("Deposit Failed : Account {} Not Found", accountNumber);

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

        logger.info(
                "Deposit Successful | Account : {} | Amount : {} | Balance : {}",
                accountNumber,
                amount,
                updated.getBalance()
        );

        return updated;
    }

    // Withdraw
    public Account withdraw(String accountNumber, Double amount) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElse(null);

        if (account == null) {

            logger.error("Withdraw Failed : Account {} Not Found", accountNumber);

            throw new RuntimeException("Account Not Found");
        }

        if (account.getBalance() < amount) {

            logger.error(
                    "Withdraw Failed : Insufficient Balance | Account : {}",
                    accountNumber
            );

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

        logger.info(
                "Withdraw Successful | Account : {} | Amount : {} | Balance : {}",
                accountNumber,
                amount,
                updated.getBalance()
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

            logger.error(
                    "Transfer Failed : Insufficient Balance | Account : {}",
                    request.getFromAccount()
            );

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

        logger.info(
                "Transfer Successful | From : {} | To : {} | Amount : {}",
                request.getFromAccount(),
                request.getToAccount(),
                request.getAmount()
        );

        return "Transfer Successful";
    }

    // Balance Enquiry
    public Double getBalance(String accountNumber) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account Not Found"));

        logger.info(
                "Balance Enquiry | Account : {} | Balance : {}",
                accountNumber,
                account.getBalance()
        );

        return account.getBalance();
    }
}