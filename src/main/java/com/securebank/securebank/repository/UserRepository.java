package com.securebank.securebank.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securebank.securebank.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}