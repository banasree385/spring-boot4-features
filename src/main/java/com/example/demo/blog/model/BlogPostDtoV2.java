package com.example.demo.blog.model;

/**
 * V2 response DTO — the external API contract for version 2.
 *
 * Breaking change from V1:
 * - 'title' (single string) is replaced by 'shortTitle' + 'titleDetails' (two separate fields)
 * - V1 clients expecting 'title' will break if they migrate to V2 without changes
 */
public record BlogPostDtoV2(
        Integer id,
        Integer userId,
        String shortTitle,      // breaking change: replaces V1 'title'
        String titleDetails,    // breaking change: replaces V1 'title'
        String body
) {

    // Factory method to map from internal domain model
    public static BlogPostDtoV2 from(BlogPost post) {
        String[] parts = post.title().split(" ", 2);
        return new BlogPostDtoV2(
                post.id(),
                post.userId(),
                parts[0],
                parts.length > 1 ? parts[1] : "",
                post.body()
        );
    }
}

