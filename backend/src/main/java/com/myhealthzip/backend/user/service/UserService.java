package com.myhealthzip.backend.user.service;

import com.myhealthzip.backend.user.dto.NewUserDto;
import com.myhealthzip.backend.user.exception.UserInputNotCompletedException;
import com.myhealthzip.backend.user.model.User;
import com.myhealthzip.backend.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Clock clock;

    public UserService(UserRepository userRepository, Clock clock) {
        this.userRepository = userRepository;
        this.clock = clock;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createAUser(NewUserDto newUserDto) {
        if (newUserDto == null || newUserDto.username() == null || newUserDto.password() == null) {
            throw new UserInputNotCompletedException("The username and password cannot be null. ");
        }

        User userToSave = new User();
        userToSave.setUsername(newUserDto.username());
        userToSave.setPassword(newUserDto.password());
        userToSave.setCreatedTime(Instant.now(clock));

        return userRepository.save(userToSave);
    }
}
