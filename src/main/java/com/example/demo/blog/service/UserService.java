package com.example.demo.blog.service;

import com.example.demo.blog.client.BlogPostClient;
import com.example.demo.blog.client.UserClient;
import com.example.demo.blog.model.User;
import com.example.demo.blog.model.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserClient userClient;
    @Autowired(required = false)
    public UserService(UserClient userClient) {
        this.userClient = userClient;
    }

    public List<User> findAll() {
        return userClient.findAll();
    }

    public User findById(Integer id) {
        return userClient.findById(id);
    }
    public User create(UserRequest request) {
        return userClient.create(request);
    }

    public User update(Integer id, UserRequest request) {
        return userClient.update(id, request);
    }

    public void delete(Integer id) {
        userClient.delete(id);
    }
}
