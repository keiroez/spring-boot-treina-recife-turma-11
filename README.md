# Projeto Vendas — Spring Boot

API REST de um sistema de vendas desenvolvida como material didático para a turma 11 do Treina Recife. O projeto demonstra CRUD genérico com generics, consultas dinâmicas com JPA Specifications, paginação e o padrão Strategy aplicado a validações de negócio.

## Tecnologias

- Java 17+ · Spring Boot 4.x
- Spring Data JPA + Hibernate
- MySQL
- Flyway (migrations)
- ModelMapper
- Lombok
- SpringDoc OpenAPI (Swagger UI)

## Pré-requisitos

- JDK 17+
- MySQL rodando em `localhost`

Crie o banco antes de subir a aplicação:

```sql
CREATE DATABASE treinaSpring;
```

As tabelas são criadas automaticamente pelo Flyway na primeira execução.

> Credenciais padrão configuradas em `src/main/resources/application.yaml`: usuário `root`, senha `root`. Altere conforme seu ambiente.

## Executando

```bash
./mvnw spring-boot:run          # Linux/Mac
mvnw.cmd spring-boot:run        # Windows
```

A API fica disponível em `http://localhost:8080`.
Documentação interativa: `http://localhost:8080/swagger-ui.html`

## Testes

```bash
# Todos os testes
mvnw.cmd test

# Uma classe específica
mvnw.cmd test -Dtest=ProdutoServiceTest

# Um método específico
mvnw.cmd test -Dtest=ProdutoServiceTest#deveRetornarTodosOsProdutosQuandoFiltrosForemNulos
```

## Endpoints

### Produtos

| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/produtos` | Lista todos |
| `GET` | `/produtos/{id}` | Busca por ID |
| `POST` | `/produtos` | Cadastra |
| `PUT` | `/produtos` | Atualiza |
| `DELETE` | `/produtos/{id}` | Remove |
| `GET` | `/produtos/page?page=0&size=20` | Lista paginada |
| `GET` | `/produtos/filtro?nome=x&precoMin=10&precoMax=100` | Filtros dinâmicos (todos opcionais) |

### Pedidos

| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/pedidos` | Lista todos |
| `GET` | `/pedidos/{id}` | Busca por ID |
| `POST` | `/pedidos` | Cadastra (executa validações) |
| `PUT` | `/pedidos` | Atualiza |
| `DELETE` | `/pedidos/{id}` | Remove |

## Arquitetura

```
controller/          Endpoints REST — extendem AbstractController<Entidade, DTO>
  request/           DTOs de entrada e saída (nunca expõe a entidade JPA diretamente)
domain/
  models/            Entidades JPA (Produto, Pedido)
  repository/        Interfaces Spring Data JPA
  services/          Regras de negócio — implementam CRUDInterface<T>
infra/               Configuração Spring (@Bean ModelMapper)
```

### Padrões demonstrados

**CRUD genérico com Generics**
`AbstractController<T, D>` centraliza os endpoints de CRUD. Subclasses passam apenas a entidade e o DTO — todo o mapeamento e roteamento é herdado.

**Strategy — validações de pedido**
`ValidacaoInclusao` é a interface da estratégia. Cada regra (`ValidacaoNomeVazio`, `ValidarPrecoService`, `ValidacaoDisponibilidadeService`) é um `@Component` independente. O `PedidoService` recebe uma `List<ValidacaoInclusao>` injetada automaticamente pelo Spring e as executa sem conhecer os detalhes de cada uma.

**JPA Specifications — filtros dinâmicos**
`ProdutoSpecifications` define critérios de filtro isolados (por nome, preço mínimo, preço máximo). O `ProdutoService` os compõe com `.and()` conforme os parâmetros recebidos, gerando a query dinamicamente sem JPQL manual.
