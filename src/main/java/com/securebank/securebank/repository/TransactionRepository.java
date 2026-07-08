package com.securebank.securebank.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securebank.securebank.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // All transactions of an account
    List<Transaction> findByFromAccountOrToAccount(
            String fromAccount,
            String toAccount
    );

    // Last 10 transactions (Mini Statement)
    List<Transaction> findTop10ByFromAccountOrToAccountOrderByTransactionDateDesc(
            String fromAccount,
            String toAccount
    );
}