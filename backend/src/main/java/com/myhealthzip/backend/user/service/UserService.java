package com.myhealthzip.backend.user.service;

import com.myhealthzip.backend.user.dto.NewUserDto;
import com.myhealthzip.backend.user.model.User;
import com.myhealthzip.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createAUser(NewUserDto newUserDto) {
        User newUser = new User();
        newUser.setUsername(newUserDto.username());
        newUser.setPassword(newUserDto.password());
        newUser.setCreatedTime(Instant.now());
        return userRepository.save(newUser);
    }
}
