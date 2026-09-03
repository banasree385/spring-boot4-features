# Spring Boot 4 — API Versioning Demo

## Introduction

- **APIs evolve** — fields added, fields removed, types changed — existing clients can break
- **Manual versioning** — teams roll their own solutions (interceptors, filters, URL tricks) leading to inconsistencies across teams
- **Spring Boot 4** — makes API versioning a first-class built-in feature, one consistent approach for all teams
- **This demo** — versioning the Blog Post API: what to consider, and how the new Spring Boot 4 mechanism works


---

## Project structure

```
blog/
├── config/
│   └── ApiVersioningConfig.java       ← versioning strategy configured here
├── controller/
│   ├── BlogPostController.java        ← V1 controller  (version = "1")
│   ├── BlogPostV2Controller.java      ← V2 controller  (version = "2")
│   └── ManualVersioningExample.java   ← old manual approach for comparison
├── model/
│   ├── BlogPost.java                  ← internal domain model (has internalNote — never exposed)
│   ├── BlogPostDtoV1.java             ← V1 response contract  (title as single string)
│   ├── BlogPostDtoV2.java             ← V2 response contract  (shortTitle + titleDetails)
│   └── BlogPostRequest.java           ← input DTO — shared by V1 and V2
└── service/
    └── BlogPostService.java           ← version-agnostic, always works with BlogPost
```

---

## Old approaches — manual versioning (before Spring Boot 4)

| Strategy | Example | Problem |
|---|---|---|
| **URL path** | `/api/v1/blog-posts` | Version hardcoded in every controller, no central config |
| **Header** | `X-API-Version: 1` | Custom filter/interceptor needed to read and route |
| **Media type** | `Accept: application/vnd.myapp.v1+json` | Complex, not discoverable, hard to test |

> Each team rolled their own solution → **inconsistency across teams**.
> No standard, no shared config, no built-in version validation.

---

## Spring Boot 4 solution

```java
// ApiVersioningConfig.java
@Override
public void configureApiVersioning(ApiVersionConfigurer configurer) {
    configurer
        .usePathSegment(1)              // /api/{version}/blog-posts
        .setDefaultVersion("1")         // no version → defaults to V1
        .addSupportedVersions("1", "2");// V3 → rejected automatically
}
```



---

## V1 vs V2 response

Same URL pattern, same service, different response shape:

| Field | V1 (`BlogPostDtoV1`) | V2 (`BlogPostDtoV2`) |
|---|---|---|
| `id` | ✅ | ✅ |
| `userId` | ✅ | ✅ |
| `title` | ✅ single string | ❌ removed — **breaking change** |
| `shortTitle` | ❌ | ✅ first word of title |
| `titleDetails` | ❌ | ✅ rest of title |
| `body` | ✅ | ✅ |
| `internalNote` | ❌ never exposed | ❌ never exposed |

---

## DTO pattern — why it matters

```
JSONPlaceholder JSON
        ↓
  BlogPost (internal domain model)
        ↓
  BlogPostDtoV1  ──→  V1 API response
  BlogPostDtoV2  ──→  V2 API response
```

- Internal model is version-agnostic
- Service always works with `BlogPost`
- Only controller layer knows about DTOs
- `internalNote` field exists in model — never mapped to any DTO

---

## Live demo endpoints

```
# V1 — title as single string
GET http://localhost:8080/api/1/blog-posts
GET http://localhost:8080/api/1/blog-posts/1

# V2 — shortTitle + titleDetails (breaking change)
GET http://localhost:8080/api/2/blog-posts
GET http://localhost:8080/api/2/blog-posts/1

# Invalid version — Spring rejects
GET http://localhost:8080/api/3/blog-posts

# Manual versioning comparison
GET http://localhost:8080/manual/v1/blog-posts
GET http://localhost:8080/manual/v2/blog-posts
GET http://localhost:8080/manual/v99/blog-posts   ← returns 404 (no handler)
```

---

## Manual vs Spring Boot 4 — side by side

| | Manual approach | Spring Boot 4 |
|---|---|---|
| Version in URL | Hardcoded in every `@RequestMapping` | Configured once in `ApiVersionConfigurer` |
| Switch strategy | Change every controller | Change one line in config |
| Default version | Not supported — client must always specify | `setDefaultVersion("1")` |
| Invalid version | Returns 404 | Returns proper error response |
| Add new version | New controller class + new URL pattern | New controller with `version = "3"` |

---

## Things to keep in mind

> **Version only breaking changes** — adding optional fields or new endpoints is non-breaking and does not need a new version.

> **Avoid too many versions** — every active version must be supported, tested and maintained. Aim for at most 2 active versions at a time.

> **Sunset responsibly** — when removing a version, announce early, add `Deprecation` + `Sunset` response headers, set a clear date, and never remove without notice.

---

