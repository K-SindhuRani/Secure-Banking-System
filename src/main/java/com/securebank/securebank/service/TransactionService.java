package com.securebank.securebank.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securebank.securebank.entity.Transaction;
import com.securebank.securebank.repository.TransactionRepository;

@Service
public class TransactionService {

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

        return transactionRepository.save(transaction);
    }

    // Get All Transactions
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    // Get Transactions By Account Number
    public List<Transaction> getTransactions(String accountNumber) {
        return transactionRepository.findByFromAccountOrToAccount(
                accountNumber,
                accountNumber);
    }

    // Get Mini Statement (Latest 5 Transactions)
    public List<Transaction> getMiniStatement(String accountNumber) {

        return transactionRepository
                .findTop5ByFromAccountOrToAccountOrderByTransactionDateDesc(
                        accountNumber,
                        accountNumber);
    }
}