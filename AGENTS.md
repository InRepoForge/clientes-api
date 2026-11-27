# AGENTS.md — clientes-api

API REST de clientes (Java 21, Spring Boot 3, Maven). Pacote base: `com.exemplo.clientes`.

## Arquitetura

- Camadas: `api` (controllers e DTOs), `domain` (entidades), `service`, `repository`.
- DTOs são `record` Java; entidades JPA nunca são expostas na API.
- Erros são tratados num único `@RestControllerAdvice` (`api/ApiExceptionHandler`), no formato
  `{"status": int, "error": string, "message": string}`.

## Testes

- JUnit 5 + Mockito para services; `@WebMvcTest` para controllers; `@DataJpaTest` para repositórios.
- Nomes de testes em português, no formato `deveFazerAlgoQuandoCondicao`.
- Os testes usam H2; não use Testcontainers.

## Regras

- Rode `mvn -B -q -o test` antes de concluir.
- Não altere o `pom.xml` sem necessidade explícita.
- Não adicione dependências: o build roda offline (`-o`).
