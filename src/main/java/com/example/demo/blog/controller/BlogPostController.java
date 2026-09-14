package com.example.demo.blog.controller;

import com.example.demo.blog.model.BlogPostDtoV1;
import com.example.demo.blog.model.BlogPostRequest;
import com.example.demo.blog.service.BlogPostService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = "/api/{version}/blog-posts", version = "1")  // path: /api/1/blog-posts
public class BlogPostController {
    private static final Logger logger= LoggerFactory.getLogger(BlogPostController.class);

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping
    public List<BlogPostDtoV1> findAll() throws InterruptedException {
        logger.info("Find all blog posts");
        Thread.sleep(500);
        return blogPostService.findAll().stream()
                .map(BlogPostDtoV1::from)
                .toList();
    }

    @GetMapping("/{id}")
    public BlogPostDtoV1 findById(@PathVariable Integer id) {
        logger.info("Find  blog posts by id");
        return BlogPostDtoV1.from(blogPostService.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPostDtoV1 create(@Valid @RequestBody BlogPostRequest request) {
        return BlogPostDtoV1.from(blogPostService.create(request));
    }

    @PutMapping("/{id}")
    public BlogPostDtoV1 update(@PathVariable Integer id, @Valid @RequestBody BlogPostRequest request) {
        return BlogPostDtoV1.from(blogPostService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        blogPostService.delete(id);
    }
}

