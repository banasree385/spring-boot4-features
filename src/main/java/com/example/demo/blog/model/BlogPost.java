package com.example.demo.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Internal domain model — used by service and client layers.
 * Not exposed directly to API clients — mapped to BlogPostDtoV1 or BlogPostDtoV2.
 *
 * 'internalNote' is an internal field — never mapped to any DTO.
 * 'ignoreUnknown = true' ensures extra fields from JSONPlaceholder JSON are ignored.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BlogPost(Integer id, Integer userId, String title, String body, String internalNote) {
}
