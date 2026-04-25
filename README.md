# Financial Wallet API - Clean Architecture Implementation

**Goal:** Demonstrating decoupled architecture, Domain-Driven Design (DDD) principles, and high test coverage in a Java/Spring Boot environment.

[![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://codespaces.new/leosoulmon/Wallet-API)

## Tech Stack

- **Java 17** + **Spring Boot 3.2**
- **Spring Data JPA** + **PostgreSQL 15**
- **Docker** + **Docker Compose** (full stack)
- **JUnit 5** + **Mockito**
- **Maven**
- **springdoc-openapi 2.5** (Swagger UI + OpenAPI 3.0)

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

## Zero-install options

### Option A — GitHub Codespaces (run in the browser)

Click **Open in GitHub Codespaces** above. The environment will automatically run `docker compose up --build` and forward port `8080`. No local install needed.


## Prerequisites (local)

- **Docker** installed and running

## Running

```bash
docker compose up --build
```

That's it. Docker Compose will:
1. Start a PostgreSQL 15 container and initialize the schema
2. Build and start the API container
3. Wait for the database to be healthy before starting the API

The API will be available at `http://localhost:8080`.

> **Subsequent runs** (no code changes): `docker compose up`  
> **Stop everything**: `docker compose down`  
> **Reset database**: `docker compose down -v && docker compose up --build`

## API Documentation (Swagger)

Once the application is running, the following endpoints are available:

| URL | Description |
|-----|-------------|
| `http://localhost:8080/swagger-ui.html` | Interactive Swagger UI — browse and test all endpoints in the browser |
| `http://localhost:8080/v3/api-docs` | Raw OpenAPI 3.0 JSON spec |

### Using Swagger UI

1. Start the application with `docker compose up --build`
2. Open `http://localhost:8080/swagger-ui.html` in your browser
3. Expand any endpoint group (`user-controller`, `account-controller`, `transaction-controller`)
4. Click an endpoint → **Try it out** → fill in the fields → **Execute**

> The JSON spec at `/v3/api-docs` can be imported directly into Postman, Insomnia, or any OpenAPI-compatible tool.

## Running locally (without Docker for the app)

```bash
docker compose up -d postgres   # start only the DB
./mvnw spring-boot:run          # run the app on the host
```

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
