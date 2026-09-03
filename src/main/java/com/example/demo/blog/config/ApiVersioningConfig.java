package com.example.demo.blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiVersioningConfig implements WebMvcConfigurer {

    // ── ACTIVE: path segment versioning (/api/1/blog-posts, /api/2/blog-posts) ──
    @Override
    public void configureApiVersioning(ApiVersionConfigurer configurer) {
        configurer
                .usePathSegment(1)              // 0-based: /api=0, {version}=1, blog-posts=2
                .setDefaultVersion("1")
                .addSupportedVersions("1", "2");
    }

    // ── ALTERNATIVE: header-based versioning (X-API-Version: 1) ──
    // @Override
    // public void configureApiVersioning(ApiVersionConfigurer configurer) {
    //     configurer
    //             .useRequestHeader("X-API-Version")
    //             .setDefaultVersion("1")
    //             .addSupportedVersions("1", "2");
    // }

    // ── ALTERNATIVE: media type parameter versioning (Accept: application/json;version=1) ──
    // @Override
    // public void configureApiVersioning(ApiVersionConfigurer configurer) {
    //     configurer
    //             .useMediaTypeParameter(org.springframework.http.MediaType.APPLICATION_JSON, "version")
    //             .setDefaultVersion("1")
    //             .addSupportedVersions("1", "2");
    // }
}
