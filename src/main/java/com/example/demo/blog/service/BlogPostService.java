package com.example.demo.blog.service;

import com.example.demo.blog.client.BlogPostClient;
import com.example.demo.blog.client.LegacyBlogPostClient;
import com.example.demo.blog.client.LegacyBlogPostRestClient;
import com.example.demo.blog.model.BlogPost;
import com.example.demo.blog.model.BlogPostRequest;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogPostService {

    private static final Logger log = LoggerFactory.getLogger(BlogPostService.class);
    private final BlogPostClient client;
    //private final LegacyBlogPostRestClient client;
    //private final LegacyBlogPostClient client;

    public BlogPostService(BlogPostClient client) {
        this.client =  client;
    }

    public List<BlogPost> findAll() {
        Tracer tracer = GlobalOpenTelemetry.getTracer("demo");
        Span span = tracer
                .spanBuilder("jsonplaceholder.findAll")
                .setSpanKind(SpanKind.CLIENT)
                .startSpan();
        try (Scope scope = span.makeCurrent()) {
            log.info("Fetching all blog posts from external API");
        return client.findAll();
        } finally {
            span.end();
        }
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

