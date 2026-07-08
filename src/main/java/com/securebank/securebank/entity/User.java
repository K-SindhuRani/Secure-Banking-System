package com.securebank.securebank.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full Name is required")
    @Size(min = 3, max = 50, message = "Full Name should be between 3 and 50 characters")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must contain at least 8 characters")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "Phone Number is required")
    @Pattern(regexp = "^[0-9]{10}$",
            message = "Phone Number must contain exactly 10 digits")
    @Column(unique = true)
    private String phone;

    @NotBlank(message = "Aadhar Number is required")
    @Pattern(regexp = "^[0-9]{12}$",
            message = "Aadhar Number must contain exactly 12 digits")
    @Column(unique = true)
    private String aadharNumber;

    @NotBlank(message = "Address is required")
    private String address;

    private String role = "CUSTOMER";

    private String status = "ACTIVE";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Account> accounts;
}