
package com.example.demo.blog.client;

import com.example.demo.blog.model.BlogPost;
import com.example.demo.blog.model.BlogPostRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;



@Component
public class LegacyBlogPostClient {


    private final RestClient restClient;

    public LegacyBlogPostClient(RestClient.Builder restClientBuilder,
                                @Value("${blog.api.base-url}") String baseUrl) {
        this.restClient = restClientBuilder.clone()
                .baseUrl(baseUrl)
                .defaultHeaders(headers -> headers.setContentType(MediaType.APPLICATION_JSON))
                .build();
    }

    public List<BlogPost> findAll() {
        return restClient.get()
                .uri("/posts")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});// ← deserialize JSON response into BlogPost.ParameterizedTypeReference needed for generic List<BlogPost>
    }


    public BlogPost findById(Integer id) {
        return restClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .body(BlogPost.class);
    }

    public BlogPost create(BlogPostRequest request) {
        return restClient.post()
                .uri("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)   // ← serialize BlogPostRequest into JSON and send it
                .retrieve()
                .body(BlogPost.class);
    }

    public BlogPost update(Integer id, BlogPostRequest request) {
        return restClient.put()
                .uri("/posts/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(BlogPost.class);
    }

    public void delete(Integer id) {
        restClient.delete()
                .uri("/posts/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}



