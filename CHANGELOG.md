# Changelog

All notable changes to this project are documented here. This project adheres to [Keep a Changelog](https://keepachangelog.com/en/1.1.0/) and [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [Unreleased]

### Added
- `springdoc-openapi` (Swagger UI) dependency added to allow endpoint testing at runtime via `http://localhost:8080/swagger-ui.html`.
- Docker configuration files added; `docker-compose.yml` and `Dockerfile` adjusted; temporary DB configuration included for local development.

---

## [1.0.0] — 2026-04-10

### Added
- Password-protected operations: deposit, transfer, and user update now require password in request body.
- User information update endpoint (`PUT /api/users/{userId}`) implemented.
- POST endpoints now return structured JSON responses with `message` and `data` fields.
- Unit tests adjusted and stabilised across use cases.

### Fixed
- Response format polished for consistency across all write endpoints.

---

## [0.3.0] — 2026-04-10

### Added
- Application configured to consume a PostgreSQL 15 database.
- `docker-compose.yml` introduced for full-stack local setup (API + PostgreSQL).
- `.devcontainer/devcontainer.json` added for GitHub Codespaces support.

---

## [0.2.0] — 2026-04-06

### Added
- Use cases implemented: `CreateUser`, `UpdateUser`, `Deposit`, `Transfer`, `GetBalance`, `GetHistory`.
- Persistence adapters wired to Spring Data JPA repositories.
- REST controllers and `GlobalExceptionHandler` added.

### Fixed
- Clean Architecture violations resolved; domain layer fully decoupled from Spring and JPA.

---

## [0.1.0] — 2026-04-06

### Added
- Initial project structure following Clean Architecture (domain / application / infrastructure).
- Domain entities: `User`, `Account`, `Transaction` with encapsulated domain methods (e.g., `account.debit(amount)`).
- Repository output-port interfaces defined in the domain layer.
- Maven wrapper added.
- Unit tests written with JUnit 5 and Mockito; `BigDecimal` boundary bug fixed.

---

## [0.0.1] — 2026-04-06

### Added
- Initial commit with project scaffolding.
- `ROADMAP.md` created outlining the four development phases and future improvement areas.
- GitHub Actions workflow configured with `develop` branch trigger.
- `README.md` with project goal, architecture overview, and API reference.

---

[Unreleased]: https://github.com/leosoulmon/Wallet-API/compare/v1.0.0...HEAD
[1.0.0]: https://github.com/leosoulmon/Wallet-API/compare/v0.3.0...v1.0.0
[0.3.0]: https://github.com/leosoulmon/Wallet-API/compare/v0.2.0...v0.3.0
[0.2.0]: https://github.com/leosoulmon/Wallet-API/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/leosoulmon/Wallet-API/compare/v0.0.1...v0.1.0
[0.0.1]: https://github.com/leosoulmon/Wallet-API/releases/tag/v0.0.1
