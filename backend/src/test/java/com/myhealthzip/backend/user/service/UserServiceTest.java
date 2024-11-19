package com.myhealthzip.backend.user.service;

import com.myhealthzip.backend.user.dto.NewUserDto;
import com.myhealthzip.backend.user.model.User;
import com.myhealthzip.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserService(userRepository);

    private final Instant createdTime = Instant.now();

    @Test
    void getAllUsersTest_whenDBIsEmpty_thenReturnEmptyList() {
        // GIVEN
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);

        // WHEN
        List<User> actual = userService.getAllUsers();

        // THEN
        verify(userRepository, times(1)).findAll();

        assertThat(actual.isEmpty());
    }

    @Test
    void getAllUsersTest_whenDBHasData_thenReturnListofUsers() {
        // GIVEN
        List<User> users = List.of(
                new User(1, "user1", "user1", createdTime),
                new User(2, "user2", "user2", createdTime)
        );
        when(userRepository.findAll()).thenReturn(users);

        // WHEN
        List<User> actual = userService.getAllUsers();

        // THEN
        List<User> expected = List.of(
                new User(1, "user1", "user1", createdTime),
                new User(2, "user2", "user2", createdTime)
        );

        verify(userRepository, times(1)).findAll();
        assertEquals(expected, actual);
    }
}