# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Stack

- **Spring Boot 4** + **Java 21** + **Maven**
- **MySQL** (local, banco `gestao_projetos`) + **Flyway** para migrações
- **Spring Security 7** + **JWT** (Auth0 `java-jwt`)
- **JPA/Hibernate**, **Bean Validation**, **Lombok**, **BCrypt**
- **Java Records** para todos os DTOs
- **RFC 7807 ProblemDetail** para tratamento de erros
- **SpringDoc OpenAPI 3.x** (`springdoc-openapi-starter-webmvc-ui`) — Swagger UI em `/swagger-ui/index.html`

## Comandos

```bash
# Rodar a aplicação
./mvnw spring-boot:run

# Build
./mvnw clean package

# Testes
./mvnw test

# Teste específico
./mvnw test -Dtest=NomeDaClasse
```

## Variáveis de ambiente

Criadas no arquivo `.env` na raiz (não commitado):
```
DB_USERNAME=root
DB_PASSWORD=root
JWT_SECRET=...
```
O `application.properties` importa via `spring.config.import=optional:file:.env[.properties]`.

## Arquitetura

### Camadas
```
controller → service → repository → entity
```
- **Controller**: HTTP, DTOs, `@PreAuthorize`
- **Service**: 100% da lógica de negócio, `@Transactional`
- **Repository**: `JpaRepository` + queries paginadas (`Pageable`)
- **Entity**: `@Entity` + JPA + Lombok (`@Getter/@Setter/@NoArgsConstructor/@AllArgsConstructor`)

### Pacote base: `com.example.gestao`
```
config/          ← SecurityConfig, OpenApiConfig
controller/      ← Auth, Usuario, Projeto, Tarefa
dto/             ← auth/, usuario/, projeto/, tarefa/ (Java Records)
entity/          ← Usuario (implements UserDetails), Projeto, Tarefa
enums/           ← StatusProjeto, StatusTarefa, Prioridade
exception/       ← ResourceNotFoundException, UsuarioOcupadoException, GlobalExceptionHandler
repository/      ← UsuarioRepository, ProjetoRepository, TarefaRepository
security/        ← TokenService, SecurityFilter (OncePerRequestFilter)
service/         ← UsuarioService (implements UserDetailsService), ProjetoService, TarefaService
validation/      ← ValidadorTarefa (interface) + validadores/ (@Component)
```

### Migrações Flyway
`src/main/resources/db/migration/`:
- `V1__create_usuarios.sql`
- `V2__create_projetos.sql`
- `V3__create_tarefas.sql`

## Regras de negócio críticas

### Enums salvos como STRING
Todos os enums (`StatusProjeto`, `StatusTarefa`, `Prioridade`) usam `@Enumerated(EnumType.STRING)`.

### DTOs nunca expostos como Entity
Controllers recebem/devolvem apenas DTOs. `fromEntity()` estático nos ResponseDTOs faz o mapeamento.

### Strategy de validações de Tarefa
`TarefaService` injeta `List<ValidadorTarefa>` e itera com `.forEach()` antes de persistir.
Cada validação é um `@Component` isolado implementando `ValidadorTarefa`.
Validadores ativos: `ValidadorProjetoAtivo` (projeto deve ser `EM_ANDAMENTO`), `ValidadorResponsavelExiste`.

### Segurança
- `POST /login` e `POST /usuarios` são públicos; demais rotas exigem JWT válido.
- Token JWT contém: subject=email, claim `role`.
- `SecurityFilter` lê `Authorization: Bearer <token>` e injeta o contexto de segurança.
- Controle granular via `@PreAuthorize("hasRole('ADMIN')")` nos Controllers.
- Rotas do Swagger (`/swagger-ui/**`, `/v3/api-docs/**`) liberadas sem autenticação em `SecurityConfig`.

### JPA e Flyway
- `spring.jpa.hibernate.ddl-auto=none` — Flyway é o único responsável pelo schema.
- Flyway usa defaults do Spring Boot: localização `classpath:db/migration`, habilitado automaticamente com `flyway-core` no classpath.
- **Não usar `ddl-auto=validate`** com Spring Boot 4.x: o Hibernate valida antes do Flyway criar as tabelas (bug de ordering).
