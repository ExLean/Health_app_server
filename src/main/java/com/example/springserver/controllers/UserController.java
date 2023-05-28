package com.example.springserver.controllers;

import com.example.springserver.models.User;
import com.example.springserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/user/{userId}")
    public User getUserById(@PathVariable int userId) {
        return service.getUserById(userId);
    }

    @GetMapping("/user/get-all")
    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    @PostMapping("/user/login")
    public User getUserByLoginData(@RequestBody User user) {
        return service.getUserByLoginData(user);
    }

    @PostMapping("/user/create")
    public User createUser(@RequestBody User user) {
        return service.createUser(user);
    }

    @PutMapping("/user/update")
    public User updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }

    @DeleteMapping("/user/delete/{userId}")
    public void delete(@PathVariable int userId) {
        service.delete(userId);
    }
}
