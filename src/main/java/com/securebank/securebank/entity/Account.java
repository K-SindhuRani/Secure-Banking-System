package com.securebank.securebank.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String accountNumber;

    @Column(nullable = false)
    private String accountType;      // SAVINGS / CURRENT

    @Column(nullable = false)
    private Double balance;

    private LocalDate createdDate;

    @Column(nullable = false)
    private String status;           // ACTIVE / BLOCKED

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}