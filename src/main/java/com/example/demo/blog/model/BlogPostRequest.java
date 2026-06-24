package com.example.demo.blog.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BlogPostRequest(
        @NotBlank String title,
        @NotBlank String body,
        @NotNull @Positive Integer userId
) {
}

