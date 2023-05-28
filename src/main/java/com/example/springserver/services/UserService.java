package com.example.springserver.services;

import com.example.springserver.exceptions.UserAlreadyExistsException;
import com.example.springserver.exceptions.UserNotFoundException;
import com.example.springserver.models.User;
import com.example.springserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(User createUser) {
        List<User> allUsers = new ArrayList<>();
        Streamable.of(userRepository.findAll()).forEach(allUsers::add);

        for (User usr : allUsers) {
            if (usr.getUsername().equals(createUser.getUsername())) {
                throw new UserAlreadyExistsException("User with name: " + createUser.getUsername() + ", already exists.");
            }
            if (usr.getEmail().equals(createUser.getEmail())) {
                throw new UserAlreadyExistsException("User with email: " + createUser.getEmail() + ", already exists.");
            }
        }

        return userRepository.save(setUser(createUser, new User()));
    }

    public User setUser(User setUser, User newUser) {
        if (setUser.getUsername() != null) {
            newUser.setUsername(setUser.getUsername());
        }
        if (setUser.getPassword() != null) {
            newUser.setPassword(setUser.getPassword());
        }
        if (setUser.getEmail() != null) {
            newUser.setEmail(setUser.getEmail());
        }
        if (setUser.getBirthday() != null) {
            newUser.setBirthday(setUser.getBirthday());
        }
        if (setUser.getHeight() != 0) {
            newUser.setHeight(setUser.getHeight());
        }
        return newUser;
    }

    public User updateUser(User updateUser) {
        List<User> allUsers = getAllUsers();

        for (User usr : allUsers) {
            if (usr.getUsername() != null && usr.getUsername().equals(updateUser.getUsername())) {
                throw new UserAlreadyExistsException("User with name: " + updateUser.getUsername() + " already exists.");
            }
            if (usr.getEmail() != null && usr.getEmail().equals(updateUser.getEmail())) {
                throw new UserAlreadyExistsException("User with email: " + updateUser.getEmail() + " already exists.");
            }
        }

        return userRepository.save(setUser(updateUser, getUserById(updateUser.getId())));
    }

    public User getUserById(int userId) {
        return userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("Stats with id: " + userId + ", does not exist."));
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        Streamable.of(userRepository.findAll()).forEach(users::add);
        return users;
    }

    public void delete(int userId) {
        userRepository.delete(getUserById(userId));
    }

    public User getUserByLoginData(User loginUser) {
        List<User> users = new ArrayList<>();
        Streamable.of(userRepository.findAll()).forEach(users::add);

        for (User user : users) {
            if (user.getUsername().equals(loginUser.getUsername()) && user.getPassword().equals(loginUser.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
