package com.myhealthzip.backend.user.controller;

import com.myhealthzip.backend.user.dto.NewUserDto;
import com.myhealthzip.backend.user.model.User;
import com.myhealthzip.backend.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createAUser(@RequestBody NewUserDto newUserDto) {
        return userService.createAUser(newUserDto);
    }
}
