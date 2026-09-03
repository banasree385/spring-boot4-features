package com.example.demo.blog.controller;

import com.example.demo.blog.model.BlogPostDtoV1;
import com.example.demo.blog.model.BlogPostDtoV2;
import com.example.demo.blog.service.BlogPostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * DEMO ONLY — shows how API versioning was done MANUALLY before Spring Boot 4.
 *
 * Problems with this approach:
 * 1. Every version needs a completely separate controller class.
 * 2. URL path version (/v1, /v2) is hardcoded in @RequestMapping — no central config.
 * 3. No default version support — clients must always specify version in URL.
 * 4. No validation of supported versions — unknown versions like /v99 just return 404.
 * 5. Adding a new version means creating yet another controller class.
 *
 * Compare with Spring Boot 4 approach:
 * - Version strategy configured once in ApiVersioningConfig
 * - Switch between path/header/media-type by changing one line
 * - Default version handled automatically
 * - Unsupported versions rejected automatically via addSupportedVersions()
 */
@RestController
public class ManualVersioningExample {

    private final BlogPostService blogPostService;

    public ManualVersioningExample(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    // ── V1 endpoint — hardcoded /v1 in URL ──────────────────────────────────
    @GetMapping("/manual/v1/blog-posts")
    public List<BlogPostDtoV1> findAllV1() {
        return blogPostService.findAll().stream()
                .map(BlogPostDtoV1::from)
                .toList();
    }

    @GetMapping("/manual/v1/blog-posts/{id}")
    public BlogPostDtoV1 findByIdV1(@PathVariable Integer id) {
        return BlogPostDtoV1.from(blogPostService.findById(id));
    }

    // ── V2 endpoint — hardcoded /v2 in URL ──────────────────────────────────
    @GetMapping("/manual/v2/blog-posts")
    public List<BlogPostDtoV2> findAllV2() {
        return blogPostService.findAll().stream()
                .map(BlogPostDtoV2::from)
                .toList();
    }

    @GetMapping("/manual/v2/blog-posts/{id}")
    public BlogPostDtoV2 findByIdV2(@PathVariable Integer id) {
        return BlogPostDtoV2.from(blogPostService.findById(id));
    }

    // ── What happens with unknown version? ──────────────────────────────────
    // GET /manual/v99/blog-posts → 404 Not Found (no handler mapped)
    // Spring Boot 4 built-in versioning would return a proper error response instead
}


