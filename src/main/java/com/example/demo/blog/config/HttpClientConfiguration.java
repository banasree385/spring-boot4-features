package com.example.demo.blog.config;

import com.example.demo.blog.client.BlogPostClient;
import com.example.demo.blog.client.UserClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientHttpServiceGroupConfigurer;
import org.springframework.web.service.registry.ImportHttpServices;

@Configuration
@ImportHttpServices(group = "users",    types = UserClient.class)
@ImportHttpServices(group = "blogs",    types = BlogPostClient.class)
public class HttpClientConfiguration {

	@Bean
	RestClient.Builder restClientBuilder() {
		return RestClient.builder();
	}


	@Bean
	RestClientHttpServiceGroupConfigurer blogsConfigurer() {
		return groups -> groups
				.filterByName("blogs")
				.forEachClient(group -> RestClient.builder()
						.baseUrl("https://jsonplaceholder.typicode.com")
						.defaultHeader("X-Client", "blogs-service")
						.defaultHeader("X-App-Version", "v1")
						.requestInterceptor((request, body, execution) -> {
							System.out.println(">>> [blogs] Headers: " + request.getHeaders());
							return execution.execute(request, body);
						}));
	}

	@Bean
	RestClientHttpServiceGroupConfigurer usersConfigurer() {
		return groups -> groups
				.filterByName("users")
				.forEachClient(group -> RestClient.builder()
						.baseUrl("https://jsonplaceholder.typicode.com")
						.defaultHeader("X-Client", "users-service")
						.defaultHeader("Authorization", "Bearer demo-token"));
	}




}

