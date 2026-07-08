package com.securebank.securebank.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securebank.securebank.entity.Transaction;
import com.securebank.securebank.repository.TransactionRepository;

@Service
public class TransactionService {

    private static final Logger logger =
            LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    private TransactionRepository transactionRepository;

    // Save Transaction
    public Transaction saveTransaction(String type,
                                       String fromAccount,
                                       String toAccount,
                                       Double amount,
                                       String status) {

        Transaction transaction = new Transaction();

        transaction.setTransactionType(type);
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setStatus(status);

        Transaction savedTransaction = transactionRepository.save(transaction);

        logger.info(
                "Transaction Saved | Type : {} | Amount : {} | Status : {}",
                type,
                amount,
                status
        );

        return savedTransaction;
    }

    // Get All Transactions
    public List<Transaction> getAllTransactions() {

        logger.info("Fetching All Transactions");

        return transactionRepository.findAll();
    }

    // Get Account Transactions
    public List<Transaction> getTransactions(String accountNumber) {

        logger.info("Fetching Transactions for Account : {}", accountNumber);

        return transactionRepository
                .findByFromAccountOrToAccount(accountNumber, accountNumber);
    }

    public List<Transaction> getMiniStatement(String accountNumber) {

    List<Transaction> transactions =
            transactionRepository
                    .findTop10ByFromAccountOrToAccountOrderByTransactionDateDesc(
                            accountNumber,
                            accountNumber);

    return transactions;
}
}