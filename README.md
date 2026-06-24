# Spring Boot 4 declarative HTTP client demo

Minimal blog post CRUD demo showing:

- Spring Boot 4 / Spring Framework 7 declarative HTTP service clients
- a separate `LegacyBlogPostClient` with the older imperative `RestClient` style for comparison

## Use case

This app exposes local endpoints under `/api/blog-posts` and forwards them to the external JSONPlaceholder blog post API.

## Declarative client structure

- `BlogPostClient` → one small declarative client for all CRUD operations
- `LegacyBlogPostClient` → old-style imperative implementation kept in a separate class for demo comparison
- `BlogPostService` → uses `blog.client.mode` to choose declarative or legacy client at startup

## External API

Configured in `src/main/resources/application.properties`:

- `blog.api.base-url=https://jsonplaceholder.typicode.com`
- `blog.client.mode=declarative` (set to `legacy` to switch)

> JSONPlaceholder is ideal for demos because it is simple and public. Its write operations are mock-style responses rather than persistent writes.

## Run

If Maven is installed locally:

```bash
mvn spring-boot:run
```

## Try the endpoints

```bash
curl http://localhost:8080/api/blog-posts
curl http://localhost:8080/api/blog-posts/1
curl -X POST http://localhost:8080/api/blog-posts \
  -H 'Content-Type: application/json' \
  -d '{"title":"Spring Boot 4 demo","body":"Declarative clients are concise.","userId":1}'
curl -X PUT http://localhost:8080/api/blog-posts/1 \
  -H 'Content-Type: application/json' \
  -d '{"title":"Updated title","body":"Updated body","userId":1}'
curl -X DELETE http://localhost:8080/api/blog-posts/1
```

## Demo talking points

1. `@ImportHttpServices(types = BlogPostClient.class)` registers an interface-based HTTP client.
2. The interface contains only endpoint contracts, not transport boilerplate.
3. `LegacyBlogPostClient` shows the repeated URI, verb, body, and response handling code that declarative clients remove.

