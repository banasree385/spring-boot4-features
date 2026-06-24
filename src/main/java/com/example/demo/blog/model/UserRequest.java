package com.example.demo.blog.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank String name,
        @NotBlank String username,
        @NotBlank @Email String email
) {
}