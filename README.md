# Financial Wallet API - Clean Architecture Implementation

**Goal:** Demonstrating decoupled architecture, Domain-Driven Design (DDD) principles, and high test coverage in a Java/Spring Boot environment.

## Tech Stack

- **Java 17** + **Spring Boot 3.2**
- **Spring Data JPA** + **PostgreSQL 15**
- **Docker** (docker-compose for database)
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
│   └── usecase/         # CreateUser, UpdateUser, Deposit, Transfer, GetBalance, GetHistory
└── infrastructure/      # Spring-specific code
    ├── persistence/     # JPA entities, Spring Data repos, adapters
    └── web/             # REST controllers, DTOs, GlobalExceptionHandler
```

## API Endpoints

All endpoints produce and consume `application/json`.

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| `POST` | `/api/users` | Create a user (auto-creates an account) | password in body |
| `PUT` | `/api/users/{userId}` | Update user name and email | password required |
| `GET` | `/api/users/{userId}` | Get user by ID | — |
| `GET` | `/api/users/{userId}/account` | Get user's account | — |
| `GET` | `/api/accounts/{accountId}/balance` | Get account balance | — |
| `POST` | `/api/accounts/{accountId}/deposit` | Deposit funds | password required |
| `POST` | `/api/accounts/{accountId}/transfer` | Transfer funds (P2P) | password required |
| `GET` | `/api/accounts/{accountId}/transactions` | Transaction history | — |

POST/PUT endpoints return a response with `message` and `data`:
```json
{
  "message": "user created successfully",
  "data": { ... }
}
```

## Prerequisites

- **Docker** installed and running

## Running

1. Start the PostgreSQL database:
```bash
docker-compose up -d
```

2. Create the database tables (first time only):
```bash
# From the walletdb directory, pipe the SQL script into the container
cat ../walletdb/walletdb-creation.sql | docker exec -i walletdb psql -U postgres -d walletdb
```

3. Start the application:
```bash
./mvnw spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Running Tests

```bash
./mvnw test
```

## Example Usage

**Create user:**
```json
POST /api/users
{ "name": "Alice", "email": "alice@example.com", "password": "secret123" }
```

**Update user:**
```json
PUT /api/users/{userId}
{ "name": "Alice Updated", "email": "newalice@example.com", "password": "secret123" }
```

**Deposit:**
```json
POST /api/accounts/{accountId}/deposit
{ "amount": 500.00, "password": "secret123" }
```

**Transfer:**
```json
POST /api/accounts/{accountId}/transfer
{ "destinationAccountId": "...", "amount": 100.00, "password": "secret123" }
