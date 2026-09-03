package com.example.demo.blog.controller;

import com.example.demo.blog.model.BlogPostDtoV2;
import com.example.demo.blog.model.BlogPostRequest;
import com.example.demo.blog.service.BlogPostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/{version}/blog-posts", version = "2")  // path: /api/2/blog-posts
public class BlogPostV2Controller {

    private final BlogPostService blogPostService;

    public BlogPostV2Controller(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping
    public List<BlogPostDtoV2> findAll() {
        return blogPostService.findAll().stream()
                .map(BlogPostDtoV2::from)
                .toList();
    }

    @GetMapping("/{id}")
    public BlogPostDtoV2 findById(@PathVariable Integer id) {
        return BlogPostDtoV2.from(blogPostService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPostDtoV2 create(@Valid @RequestBody BlogPostRequest request) {
        return BlogPostDtoV2.from(blogPostService.create(request));
    }

    @PutMapping("/{id}")
    public BlogPostDtoV2 update(@PathVariable Integer id, @Valid @RequestBody BlogPostRequest request) {
        return BlogPostDtoV2.from(blogPostService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        blogPostService.delete(id);
    }
}

