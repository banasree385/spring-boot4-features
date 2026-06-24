package com.example.demo.blog.controller;

import com.example.demo.blog.model.BlogPost;
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
@RequestMapping("/api/blog-posts")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping
    public List<BlogPost> findAll() {
        return blogPostService.findAll();
    }

    @GetMapping("/{id}")
    public BlogPost findById(@PathVariable Integer id) {
        return blogPostService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost create(@Valid @RequestBody BlogPostRequest request) {
        return blogPostService.create(request);
    }

    @PutMapping("/{id}")
    public BlogPost update(@PathVariable Integer id, @Valid @RequestBody BlogPostRequest request) {
        return blogPostService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        blogPostService.delete(id);
    }
}

