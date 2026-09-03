package com.example.demo.blog.model;

/**
 * V1 response DTO — the external API contract for version 1.
 * Exposes 'title' as a single full string.
 */
public record BlogPostDtoV1(Integer id, Integer userId, String title, String body) {

    // Factory method to map from internal domain model
    public static BlogPostDtoV1 from(BlogPost post) {
        return new BlogPostDtoV1(post.id(), post.userId(), post.title(), post.body());
    }
}

