# Bank App Backend

A comprehensive Spring Boot REST API banking application backend that enables user registration, account management, fund transfers, and statement generation.

## Overview

Built a Java Spring Boot REST API that provides comprehensive banking services including user and account management, 
fund transfers between accounts, and detailed transaction statements. The application leverages Spring Web MVC, Spring Data JPA and JPA/Hibernate ORM with 
PostgreSQL for persistent data storage and demonstrates key Spring Boot concepts including dependency injection, entity management and 
JPA advanced mappings between entities, transaction handling, global AOP based exception handling, following best practices for RESTful web API design and pagination,
and unit testing with Junit/Mockito.

## Key Features

- **User Management**: Register new users with validation and account creation, view and manage users and their accounts 
- **Multiple Account Types**: Support for Savings and Current accounts
- **Fund Transfers**: Transfer funds between different accounts with transaction tracking
- **Account Statements**: Generate paginated monthly account statements
- **Exception Handling**: Global exception handling with custom error responses
- **Data Validation**: Request validation using Jakarta validation annotations

## Tools/Software and Processes

- **Java 21** – OOP, classes, interfaces, records, collections, lambdas, method references, streams, generics, exception handling, packages
- **Spring Boot 4.0.6** – spring-boot-starter-web, spring-boot-starter-data-jpa, Spring Data JPA, JPA/Hibernate ORM, Hibernate validation, actuator endpoints, Swagger/OpenAPI documentation
- **Maven** – Build tool and dependency management
- **PostgreSQL**
- **Lombok**
- **Jakarta/Hibernate Validation** for Bean validation and constraint annotations
- **JUnit & Mockito** for Unit testing and Mocking framework for unit testing
- **REST APIs** – RESTful endpoint design with HTTP status codes and pagination 

## API Endpoints

### User Management
- `POST /api/v1/users` – Register a new user with account creation
- `GET /api/v1/users` – Retrieve all users
- `GET /api/v1/users/{userId}` – Retrieve specific user details
- `POST /api/v1/users/{userId}/accounts` – Create additional account for existing user
- `GET /api/v1/users/{userId}/accounts` – Retrieve all accounts of a user

### Transactions
- `POST /api/v1/transactions/transfers` – Transfer funds between accounts
- `POST /api/v1/statement/view` – View paginated account statement for a specific month

## Getting Started

### Prerequisites
- Java 21 JDK
- Maven 3.6+
- PostgreSQL 12+

### Installation & Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd bank-app-backend
