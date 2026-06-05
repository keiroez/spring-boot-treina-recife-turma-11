# Gestão de Projetos API

API REST para **gerenciamento de projetos e tarefas** com autenticação JWT e controle de acesso por perfil (`USER` / `ADMIN`). Projeto desenvolvido na turma 11 do Treina Recife.

Cada projeto possui um responsável e um status; cada tarefa é vinculada a um projeto e a um responsável, com prioridade e status próprios. O acesso a todos os recursos (exceto login e cadastro de usuário) exige um token JWT válido.

---

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | **Java 25** |
| Framework | **Spring Boot 4.0.6** |
| Build | **Maven** |
| Banco de dados | **MySQL** (`gestao_projetos`) |
| Migrações | **Flyway** (`flyway-mysql`) |
| Segurança | **Spring Security 7** + **JWT** (Auth0 `java-jwt` 4.5.2) + **BCrypt** |
| Persistência | **Spring Data JPA / Hibernate** |
| Validação | **Bean Validation** (Jakarta) |
| Boilerplate | **Lombok** |
| Documentação | **springdoc-openapi** (Swagger UI) |
| Padrão de erros | **RFC 7807 ProblemDetail** |

---

## Dependências (pom.xml)

**Runtime / aplicação**
- `spring-boot-starter-data-jpa` — JPA/Hibernate
- `spring-boot-starter-webmvc` — API REST
- `spring-boot-starter-security` — Spring Security
- `spring-boot-starter-flyway` + `flyway-mysql` — versionamento do schema
- `springdoc-openapi-starter-webmvc-ui` (3.0.2) — Swagger/OpenAPI
- `com.auth0:java-jwt` (4.5.2) — geração/validação de tokens JWT
- `mysql-connector-j` (runtime) — driver MySQL
- `org.projectlombok:lombok` — getters/setters/construtores
- `spring-boot-devtools` (runtime, opcional) — hot reload

**Teste**
- `spring-boot-starter-data-jpa-test`
- `spring-boot-starter-flyway-test`
- `spring-boot-starter-security-test`
- `spring-boot-starter-webmvc-test`

---

## Pré-requisitos

- **JDK 25** instalado e no `PATH` (`java -version`)
- **Maven 3.9+** (`mvn -version`) — não há Maven Wrapper (`mvnw`) neste repositório
- **MySQL** rodando em `localhost:3306`

---

## Configuração do ambiente

### 1. Criar o banco de dados

```sql
CREATE DATABASE gestao_projetos;
```

> As tabelas **não** precisam ser criadas manualmente — o Flyway aplica as migrações
> (`V1__create_usuarios`, `V2__create_projetos`, `V3__create_tarefas`) automaticamente no startup.

### 2. Ajustar credenciais e segredo do JWT

A configuração fica em `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost/gestao_projetos
    username: root      # ajuste para o seu usuário MySQL
    password: root      # ajuste para a sua senha MySQL
  jpa:
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        ddl-auto: validate   # Hibernate apenas valida; o schema é gerido pelo Flyway

api:
  security:
    token:
      secret: 123456    # segredo HMAC usado para assinar o JWT — troque em produção
```

> **Observação de segurança:** em ambiente real não versione credenciais nem o `secret`.
> Prefira variáveis de ambiente (ex.: `SPRING_DATASOURCE_USERNAME`,
> `SPRING_DATASOURCE_PASSWORD`, `API_SECURITY_TOKEN_SECRET`), que sobrescrevem os
> valores do `application.yaml`.

---

## Como rodar

```bash
# Rodar a aplicação (porta padrão 8080)
mvn spring-boot:run

# Build do pacote
mvn clean package

# Rodar todos os testes
mvn test

# Rodar um teste específico
mvn test -Dtest=NomeDaClasse

# Executar o JAR gerado
java -jar target/demo-0.0.1-SNAPSHOT.jar
```

A aplicação sobe em **http://localhost:8080**.

### Documentação interativa (Swagger)

- Swagger UI: **http://localhost:8080/swagger-ui.html**
- OpenAPI JSON: **http://localhost:8080/v3/api-docs**

No Swagger, use o botão **Authorize** e informe o token no formato `Bearer <token>` (esquema `bearer-key`).

---

## Arquitetura

### Camadas

```
controller → service → repository → entity
```

- **Controller** — entrada HTTP, recebe/devolve apenas **DTOs**, aplica `@PreAuthorize`.
- **Service** — concentra 100% da lógica de negócio, transações (`@Transactional`).
- **Repository** — `JpaRepository` com consultas paginadas (`Pageable`).
- **Entity** — mapeamento JPA com Lombok.

### Estrutura de pacotes — `com.example.gestao`

```
config/        ← SecurityConfig, OpenApiConfig
controller/    ← Auth, Usuario, Projeto, Tarefa
dto/           ← auth/, usuario/, projeto/, tarefa/ (Java Records)
entity/        ← Usuario (implements UserDetails), Projeto, Tarefa
enums/         ← StatusProjeto, StatusTarefa, Prioridade
exception/     ← ResourceNotFoundException, UsuarioOcupadoException, GlobalExceptionHandler
repository/    ← UsuarioRepository, ProjetoRepository, TarefaRepository
security/      ← TokenService, SecurityFilter (OncePerRequestFilter)
service/       ← UsuarioService (implements UserDetailsService), ProjetoService, TarefaService
validation/    ← ValidadorTarefa (interface) + validadores/ (@Component)
```

### Migrações Flyway

Em `src/main/resources/db/migration/`:

- `V1__create_usuarios.sql`
- `V2__create_projetos.sql`
- `V3__create_tarefas.sql`

### Padrões e decisões

- **DTOs em Java Records** — controllers nunca expõem entidades; o mapeamento é feito por
  `fromEntity()` estático nos ResponseDTOs.
- **Enums como STRING** — `StatusProjeto`, `StatusTarefa` e `Prioridade` usam
  `@Enumerated(EnumType.STRING)`.
- **Strategy de validação de Tarefa** — `TarefaService` injeta `List<ValidadorTarefa>` e
  itera todos os validadores antes de persistir. Cada regra é um `@Component` isolado:
  - `ValidadorProjetoAtivo` — o projeto precisa estar `EM_ANDAMENTO`.
  - `ValidadorResponsavelExiste` — o responsável informado precisa existir.
- **Tratamento global de erros** — `GlobalExceptionHandler` retorna respostas no padrão
  RFC 7807 `ProblemDetail`.

---

## Segurança e autenticação

- Autenticação **stateless** via JWT (`SessionCreationPolicy.STATELESS`), CSRF desabilitado.
- **Rotas públicas:** `POST /login`, `POST /usuarios` e as rotas do Swagger
  (`/swagger-ui/**`, `/swagger-ui.html`, `/v3/api-docs/**`). Todas as demais exigem token.
- O token JWT (issuer `gestao-api`, validade **8 horas**) contém:
  - `subject` = email do usuário
  - claim `role` = perfil do usuário
- `SecurityFilter` (`OncePerRequestFilter`) lê o header `Authorization: Bearer <token>`,
  valida e injeta o contexto de segurança.
- Controle de acesso granular por método via `@PreAuthorize` nos controllers.
- Senhas armazenadas com **BCrypt**.

### Perfis (roles)

Os usuários são criados com role `ROLE_USER` ou `ROLE_ADMIN`. Como o Spring adiciona o
prefixo `ROLE_`, as anotações usam `hasRole('USER')` / `hasRole('ADMIN')`.

---

## Endpoints

> Base URL: `http://localhost:8080`. Salvo indicação em contrário, todas as rotas exigem
> `Authorization: Bearer <token>`. Listagens são paginadas (`?page=`, `?size=`, `?sort=`).

### Autenticação — `/login`

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| POST | `/login` | Público | Autentica (email + senha) e devolve o token JWT |

### Usuários — `/usuarios`

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| POST | `/usuarios` | Público | Cadastra um novo usuário |
| GET | `/usuarios` | ADMIN | Lista usuários (paginado) |
| GET | `/usuarios/{id}` | USER, ADMIN | Busca usuário por ID |
| PUT | `/usuarios/{id}` | ADMIN | Atualiza usuário |
| DELETE | `/usuarios/{id}` | ADMIN | Remove usuário |

### Projetos — `/projetos`

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| POST | `/projetos` | USER, ADMIN | Cria projeto |
| GET | `/projetos` | USER, ADMIN | Lista projetos (paginado) |
| GET | `/projetos/{id}` | USER, ADMIN | Busca projeto por ID |
| PUT | `/projetos/{id}` | USER, ADMIN | Atualiza projeto |
| DELETE | `/projetos/{id}` | ADMIN | Remove projeto |

### Tarefas — `/tarefas`

| Método | Rota | Acesso | Descrição |
|---|---|---|---|
| POST | `/tarefas` | USER, ADMIN | Cria tarefa (projeto `EM_ANDAMENTO` + responsável existente) |
| GET | `/tarefas` | USER, ADMIN | Lista tarefas (paginado) |
| GET | `/tarefas/{id}` | USER, ADMIN | Busca tarefa por ID |
| PUT | `/tarefas/{id}` | USER, ADMIN | Atualiza tarefa |
| DELETE | `/tarefas/{id}` | ADMIN | Remove tarefa |

---

## Modelo de domínio

**Usuario** — `id`, `nome`, `email` (único), `senha` (BCrypt), `role`.

**Projeto** — `id`, `nome`, `descricao`, `status` (`StatusProjeto`), `dataInicio`, `dataFim`,
`responsavel` (→ Usuario).

**Tarefa** — `id`, `titulo`, `descricao`, `prioridade` (`Prioridade`), `status`
(`StatusTarefa`), `dataCriacao`, `dataExecucao`, `projeto` (→ Projeto),
`responsavel` (→ Usuario).

**Enums**
- `StatusProjeto`: `PLANEJADO`, `EM_ANDAMENTO`, `CONCLUIDO`, `CANCELADO`
- `StatusTarefa`: `PENDENTE`, `FAZENDO`, `CONCLUIDA`
- `Prioridade`: `BAIXA`, `MEDIA`, `ALTA`

---

## Exemplo de fluxo de uso

```bash
# 1. Cadastrar um usuário (público)
curl -X POST http://localhost:8080/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nome":"Admin","email":"admin@example.com","senha":"123456","role":"ROLE_ADMIN"}'

# 2. Login → obter token
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"email":"admin@example.com","senha":"123456"}'
# resposta: { "token": "eyJhbGciOi..." }

# 3. Usar o token nas rotas protegidas
curl http://localhost:8080/projetos \
  -H "Authorization: Bearer eyJhbGciOi..."
```
