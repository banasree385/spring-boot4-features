package com.example.demo.blog.service;

import com.example.demo.blog.client.BlogPostClient;
import com.example.demo.blog.client.LegacyBlogPostClient;
import com.example.demo.blog.client.LegacyBlogPostRestClient;
import com.example.demo.blog.model.BlogPost;
import com.example.demo.blog.model.BlogPostRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

    private final BlogPostClient client;
    //private final LegacyBlogPostRestClient client;
    //private final LegacyBlogPostClient client;

    public BlogPostService(BlogPostClient client) {
        this.client =  client;
    }

    public List<BlogPost> findAll() {
        return client.findAll();
    }

    public BlogPost findById(Integer id) {
        return client.findById(id);
    }

    public BlogPost create(BlogPostRequest request) {
        return client.create(request);
    }

    public BlogPost update(Integer id, BlogPostRequest request) {
        return client.update(id, request);
    }

    public void delete(Integer id) {
        client.delete(id);
    }
}

