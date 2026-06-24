package com.example.demo.blog.client;

import com.example.demo.blog.model.BlogPost;
import com.example.demo.blog.model.BlogPostRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class LegacyBlogPostRestClient  {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public LegacyBlogPostRestClient(RestTemplate restTemplate,
                                @Value("${blog.api.base-url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }


    public List<BlogPost> findAll() {
        return restTemplate.exchange(
                baseUrl + "/posts",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BlogPost>>() {}
        ).getBody();
    }


    public BlogPost findById(Integer id) {
        return restTemplate.getForObject(baseUrl + "/posts/{id}", BlogPost.class, id);
    }

    public BlogPost create(BlogPostRequest request) {
        return restTemplate.postForObject(baseUrl + "/posts", request, BlogPost.class);
    }

    public BlogPost update(Integer id, BlogPostRequest request) {
        return restTemplate.exchange(
                baseUrl + "/posts/{id}",
                HttpMethod.PUT,
                new HttpEntity<>(request),
                BlogPost.class,
                id
        ).getBody();
    }

    public void delete(Integer id) {
        restTemplate.delete(baseUrl + "/posts/{id}", id);
    }
}