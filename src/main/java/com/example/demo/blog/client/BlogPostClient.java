package com.example.demo.blog.client;

import com.example.demo.blog.model.BlogPost;
import com.example.demo.blog.model.BlogPostRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.DeleteExchange;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.service.annotation.PutExchange;

import java.util.List;

@HttpExchange("${blog.api.base-url}/posts")
public interface BlogPostClient {

    @GetExchange
    List<BlogPost> findAll();


    @GetExchange("/{id}")
    BlogPost findById(@PathVariable Integer id);

    @PostExchange
    BlogPost create(@RequestBody BlogPostRequest request);


    @PutExchange("/{id}")
    BlogPost update(@PathVariable Integer id, @RequestBody BlogPostRequest request);

    @DeleteExchange("/{id}")
    void delete(@PathVariable Integer id);
}

