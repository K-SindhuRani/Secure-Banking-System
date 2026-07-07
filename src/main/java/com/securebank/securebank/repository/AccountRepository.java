package com.securebank.securebank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securebank.securebank.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

}