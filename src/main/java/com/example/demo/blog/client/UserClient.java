package com.example.demo.blog.client;

import com.example.demo.blog.model.User;
import com.example.demo.blog.model.UserRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.*;

import java.util.List;
//@HttpExchange("/users")
@HttpExchange("${blog.api.base-url}/users")
public interface UserClient {

    @GetExchange
    List<User> findAll();

    @GetExchange("/{id}")
    User findById(@PathVariable Integer id);

    @PostExchange
    User create(@RequestBody UserRequest request);

    @PutExchange("/{id}")
    User update(@PathVariable Integer id, @RequestBody UserRequest request);

    @DeleteExchange("/{id}")
    void delete(@PathVariable Integer id);
}
