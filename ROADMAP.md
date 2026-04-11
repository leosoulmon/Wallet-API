# Financial Wallet API: Development Roadmap

1. Project Scope
The API must provide the following features:

User Provisioning: Create and manage user accounts.

Deposits: Credit funds into a specific account.

Peer-to-Peer (P2P) Transfers: Move funds between users with idempotency and validation (e.g., preventing insufficient funds).

Balance & Transaction History: Fetch current standing and an audit trail of movements.

2. Project Structure (Clean Architecture)
We will follow a decoupled approach to ensure the Domain remains independent of external frameworks.

domain (The Core): Contains Entities (e.g., User, Account) and Repository Interfaces (Output Ports). This layer is "Plain Old Java" (POJO) and has zero dependencies on Spring.

application (Business Logic): Contains Use Cases or Services (e.g., TransferFundsUseCase). This is where the core business rules live.

infrastructure (The Implementation): The "dirty" layer. Contains Spring Configuration, JPA Repositories, REST Controllers (Adapters), and Global Exception Handling.

3. Execution Phases
Phase 1: The Domain Layer (Core)
Define your User and Account entities. Focus on Encapsulation. Instead of just getters and setters, implement methods like account.debit(amount). Define your Repository interfaces here to dictate how the data should be handled without worrying about the database yet.

Phase 2: The Application Layer (Use Cases)
Implement the transfer logic. The service should:

Fetch the source and destination accounts via the interface.

Validate the transaction (e.g., check for sufficient balance).

Execute the balance shift.

Persist the changes.

Phase 3: The Infrastructure Layer (Web & DB)
Set up the Persistence Adapter using Spring Data JPA and create the REST Controllers. This is where you map your DTOs (Data Transfer Objects) to your Domain Entities.

Phase 4: Unit Testing & Mocking
Use JUnit 5 and Mockito. For international recruiters, a test suite demonstrating Boundary Testing (e.g., trying to transfer $0.01 more than the balance) is the ultimate "green flag."

4. GitHub Presence (The "Pitch")
Your README.md is your first interview. Avoid "Study Project." Use:

Financial Wallet API - Clean Architecture Implementation
Goal: Demonstrating decoupled architecture, Domain-Driven Design (DDD) principles, and high-test coverage in a Java/Spring Boot environment.

# Improvements pending
Logging and Observability: Integrate Spring Boot Actuator and a structured logging framework (like Logback with JSON format) to demonstrate how the system would be monitored in a cloud-native environment.

Idempotency: For financial transactions like deposits and transfers, implementing idempotency keys is a "No-BS" requirement to prevent double-processing if a network request is retried.

# Critical Areas for Improvement
To elevate this project from a demonstration of architecture to a production-ready system, consider the following enhancements:

Automated Schema Management: Currently, the project relies on a manual SQL script (walletdb-creation.sql) executed via Docker. Integrating Flyway or Liquibase would automate database migrations, ensuring consistency across different environments and version control for the schema.

Integration Testing: While JUnit 5 and Mockito cover unit tests, adding integration tests using Testcontainers would allow you to test the infrastructure layer (persistence and web) against a real PostgreSQL instance during the build process.

Security Implementation: The API requires a password for sensitive operations (deposits, transfers), but the implementation details for hashing (e.g., BCrypt) and session management (e.g., JWT or OAuth2) are not highlighted. Implementing Spring Security would provide a robust framework for authentication and authorization.

API Documentation: Adding SpringDoc OpenAPI (Swagger) would provide an interactive UI for testing endpoints and automatically generate documentation, which is essential for any public-facing or collaborative API.

Validation: Ensure that the web layer utilizes @Valid and JSR-303/JSR-380 annotations for input DTOs to catch malformed data before it reaches the application layer.