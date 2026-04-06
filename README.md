# Financial Wallet API - Clean Architecture Implementation

**Goal:** Demonstrating decoupled architecture, Domain-Driven Design (DDD) principles, and high test coverage in a Java/Spring Boot environment.

## Tech Stack

- **Java 17** + **Spring Boot 3.2**
- **Spring Data JPA** + **H2** (in-memory, dev)
- **JUnit 5** + **Mockito**
- **Maven**

## Architecture

Clean Architecture with three layers:

```
com.wallet
├── domain/              # Pure Java — zero Spring dependencies
│   ├── entity/          # User, Account, Transaction (with domain methods)
│   ├── repository/      # Output port interfaces
│   └── exception/       # Domain exceptions
├── application/         # Use Cases (business rules)
│   └── usecase/         # CreateUser, Deposit, Transfer, GetBalance, GetHistory
└── infrastructure/      # Spring-specific code
    ├── persistence/     # JPA entities, Spring Data repos, adapters
    └── web/             # REST controllers, DTOs, GlobalExceptionHandler
```

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/users` | Create a user (auto-creates an account) |
| `GET` | `/api/users/{userId}` | Get user by ID |
| `GET` | `/api/users/{userId}/account` | Get user's account |
| `GET` | `/api/accounts/{accountId}/balance` | Get account balance |
| `POST` | `/api/accounts/{accountId}/deposit` | Deposit funds |
| `POST` | `/api/accounts/{accountId}/transfer` | Transfer funds (P2P) |
| `GET` | `/api/accounts/{accountId}/transactions` | Transaction history |

## Running

```bash
./mvnw spring-boot:run
```

The H2 console is available at `http://localhost:8080/h2-console`  
JDBC URL: `jdbc:h2:mem:walletdb`

## Running Tests

```bash
./mvnw test
```

## Example Usage

**Create user:**
```json
POST /api/users
{ "name": "Alice", "email": "alice@example.com" }
```

**Deposit:**
```json
POST /api/accounts/{accountId}/deposit
{ "amount": 500.00 }
```

**Transfer:**
```json
POST /api/accounts/{accountId}/transfer
{ "destinationAccountId": "...", "amount": 100.00 }
