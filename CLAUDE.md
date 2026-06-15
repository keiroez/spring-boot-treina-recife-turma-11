# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

REST API for project/task management ("Gestão de Projetos") with JWT auth and role-based
access (`USER` / `ADMIN`). Spring Boot 4.0.6 on **Java 25**, Maven, MySQL + Flyway.
The README is in Portuguese and is the authoritative source for endpoints and the domain
model; code, packages, and identifiers are also Portuguese.

> Naming note: the Maven artifact is `demo` (groupId `com.auth.user`) so the built JAR is
> `target/demo-0.0.1-SNAPSHOT.jar`, but the Java package is `com.example.gestao` and the
> main class is `GestaoApplication`. These intentionally don't match.

## Commands

No Maven Wrapper (`mvnw`) is present — use a system `mvn` (3.9+) and JDK 25.

```bash
mvn spring-boot:run                 # run on :8080
mvn clean package                   # build the JAR
mvn test                            # all tests
mvn test -Dtest=ClassName           # single test class
mvn test -Dtest=ClassName#method    # single test method
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

All endpoints are served under the `/api/v1` prefix (`server.servlet.context-path`), so the
real paths are e.g. `POST /api/v1/login`, `GET /api/v1/projetos`. Spring Security request
matchers are still written without the prefix (Security matches the path after the
context-path), so `SecurityConfig` keeps using `/login`, `/usuarios`, etc.

Swagger UI: `http://localhost:8080/api/v1/swagger-ui.html` — use **Authorize** with `Bearer <token>`.

## Prerequisites / config

MySQL must be running on `localhost:3306` with a `gestao_projetos` database created
(`CREATE DATABASE gestao_projetos;`). Tables are managed by Flyway, **not** Hibernate
(`ddl-auto: validate`) — schema changes mean a new `V*__*.sql` migration in
`src/main/resources/db/migration/`, never an entity-only change.

Settings live in `src/main/resources/application.yaml`. DB credentials and the JWT HMAC
secret (`api.security.token.secret`) are committed with dev defaults; override via env vars
(`SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, `API_SECURITY_TOKEN_SECRET`).

## Architecture

Layered: `controller → service → repository → entity`, all under `com.example.gestao`.

- **Controllers** speak only DTOs and enforce access with `@PreAuthorize`. They never expose
  entities. DTOs are **Java Records**; response DTOs convert via a static `fromEntity(...)`.
- **Services** hold all business logic and transactions (`@Transactional`). Each service
  exposes a package-private/`buscarEntidade(id)` that throws `ResourceNotFoundException`;
  cross-service lookups go through these, not repositories directly.
- **Repositories** are `JpaRepository` with `Pageable` listings.

### Task validation is a Strategy chain (key pattern)

`TarefaService` injects `List<ValidadorTarefa>` and runs **every** validator before persist
on both create and update. To add a task business rule, create a new `@Component`
implementing `ValidadorTarefa` in `validation/validadores/` — it is auto-discovered, no
wiring needed. Existing rules: `ValidadorProjetoAtivo` (project must be `EM_ANDAMENTO`),
`ValidadorResponsavelExiste`.

### Security

Stateless JWT (`SessionCreationPolicy.STATELESS`, CSRF off). `SecurityFilter`
(`OncePerRequestFilter`) reads `Authorization: Bearer <token>`, validates via `TokenService`
(Auth0 `java-jwt`, issuer `gestao-api`, 8h TTL, subject = email, `role` claim), and populates
the security context. `Usuario` implements `UserDetails`; `UsuarioService` implements
`UserDetailsService`. Roles are stored as `ROLE_USER` / `ROLE_ADMIN`, so `@PreAuthorize` uses
`hasRole('USER')` / `hasRole('ADMIN')`. Public routes: `POST /login`, `POST /usuarios`,
and Swagger paths.

### Errors

`GlobalExceptionHandler` (`@RestControllerAdvice`) maps exceptions to RFC 7807
`ProblemDetail` responses. Throw the domain exceptions in `exception/`
(`ResourceNotFoundException`, `UsuarioOcupadoException`) rather than handling errors inline.

### Enums

`StatusProjeto`, `StatusTarefa`, `Prioridade` are persisted as `@Enumerated(EnumType.STRING)`.
