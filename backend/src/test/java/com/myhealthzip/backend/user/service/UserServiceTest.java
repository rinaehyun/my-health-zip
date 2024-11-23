package com.myhealthzip.backend.user.service;

import com.myhealthzip.backend.user.dto.NewUserDto;
import com.myhealthzip.backend.user.model.User;
import com.myhealthzip.backend.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    Clock fixedClock = Clock.fixed(Instant.parse("2024-11-19T10:00:00Z"), ZoneId.of("UTC"));

    private final UserService userService = new UserService(userRepository, fixedClock);

    private final Instant createdTime = Instant.now(fixedClock);

    @Test
    void getAllUsersTest_whenDBIsEmpty_thenReturnEmptyList() {
        // GIVEN
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);

        // WHEN
        List<User> actual = userService.getAllUsers();

        // THEN
        verify(userRepository, times(1)).findAll();

        assertThat(actual).isEmpty();
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


    @Test
    void createAUserTest_whenPayloadIsCorrect_thenReturnNewUser() {
        // GIVEN
        NewUserDto newUserDto = new NewUserDto("user1", "user1");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setUserId(1); // Simulate ID assignment by database
            return savedUser;
        });

        // WHEN
        User actual = userService.createAUser(newUserDto);

        // THEN
        User expected = new User();
        expected.setUsername(newUserDto.username());
        expected.setPassword(newUserDto.password());
        expected.setCreatedTime(createdTime);
        expected.setUserId(1);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository, times(1)).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals(expected, capturedUser);
        assertEquals(expected, actual);
    }
}