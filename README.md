# Secure Banking System

A secure banking backend application developed using Spring Boot, Spring Security, JWT Authentication, Hibernate, JPA, and MySQL.

## Features

- User Registration
- Secure Login using JWT Authentication
- BCrypt Password Encryption
- Create Bank Account
- Deposit Money
- Withdraw Money
- Transfer Money
- Transaction History
- REST APIs
- MySQL Database Integration
- Spring Security

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- JWT
- Hibernate
- Spring Data JPA
- MySQL
- Maven
- Lombok
- Postman

## Project Structure

```
controller
service
repository
entity
dto
security
config
exception
```

## API Endpoints

### Authentication

POST

```
/auth/login
```

### Users

```
POST /users
GET /users
GET /users/{id}
DELETE /users/{id}
```

### Accounts

```
POST /accounts/user/{userId}
GET /accounts
GET /accounts/{accountNumber}
PUT /accounts/deposit/{accountNumber}/{amount}
PUT /accounts/withdraw/{accountNumber}/{amount}
POST /accounts/transfer
```

## Security

- JWT Authentication
- BCrypt Password Encryption
- Stateless Session Management

## Database

MySQL

## Author

Sindhu Rani
