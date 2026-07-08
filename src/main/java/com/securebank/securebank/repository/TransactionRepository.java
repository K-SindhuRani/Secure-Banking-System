package com.securebank.securebank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securebank.securebank.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Get all transactions of an account
    List<Transaction> findByFromAccountOrToAccount(
            String fromAccount,
            String toAccount);

    // Get latest 5 transactions
    List<Transaction> findTop5ByFromAccountOrToAccountOrderByTransactionDateDesc(
            String fromAccount,
            String toAccount);
}