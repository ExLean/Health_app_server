package com.example.springserver;

import com.example.springserver.controllers.UserController;
import com.example.springserver.models.User;
import com.example.springserver.services.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void getAllUsers() {
        User user1 = new User();
        user1.setUsername("Test1");
        user1.setPassword("123");
        user1.setEmail("test@gmail.com");
        user1.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user1.setHeight(172.5F);

        User user2 = new User();
        user2.setUsername("Test2");
        user2.setPassword("123");
        user2.setEmail("test@gmail.com");
        user2.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user2.setHeight(172.5F);

        List<User> allUsers = new ArrayList<>();
        allUsers.add(user1);
        allUsers.add(user2);

        Mockito.when(userService.getAllUsers()).thenReturn(allUsers);

        List<User> users = userController.getAllUsers();

        assertEquals(2, users.size());
        assertEquals("Test1", users.get(0).getUsername());
        assertEquals("Test2", users.get(1).getUsername());
    }

    @Test
    void userLoginTest() {
        User user = new User();
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        User loginUser = new User();
        user.setUsername("Test");
        user.setPassword("123");

        Mockito.when(userService.getUserByLoginData(loginUser)).thenReturn(user);

        User loggedInUser = userController.getUserByLoginData(loginUser);

        assertEquals("Test", loggedInUser.getUsername());
        assertEquals("123", loggedInUser.getPassword());
        assertEquals("test@gmail.com", loggedInUser.getEmail());
    }

    @Test
    void updateUserTest() {
        User user = new User();
        user.setId(999);
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        Mockito.when(userService.updateUser(user)).thenReturn(user);

        User updatedUser = userController.updateUser(user);

        assertEquals("Test", updatedUser.getUsername());
        assertEquals("123", updatedUser.getPassword());
        assertEquals("test@gmail.com", updatedUser.getEmail());
    }

    @Test
    void deleteUserTest() {
        User user = new User();
        user.setId(999);
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        userController.delete(user.getId());

        Mockito.verify(userService, Mockito.times(1)).delete(user.getId());
    }

    @Test
    void getUserByIdTest() {
        User user = new User();
        user.setId(999);
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        Mockito.when(userService.getUserById(user.getId())).thenReturn(user);

        User foundUser = userController.getUserById(user.getId());

        assertEquals("Test", foundUser.getUsername());
        assertEquals("123", foundUser.getPassword());
        assertEquals("test@gmail.com", foundUser.getEmail());
    }

    @Test
    void createUserTest() {
        User user = new User();
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        Mockito.when(userService.createUser(user)).thenReturn(user);

        User newUser = userController.createUser(user);

        assertEquals("Test", newUser.getUsername());
        assertEquals("123", newUser.getPassword());
        assertEquals("test@gmail.com", newUser.getEmail());
    }
}
