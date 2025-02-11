package com.postify.main.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import com.postify.main.entities.User;
import com.postify.main.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserName("testuser");
        user.setPassword("password");
        user.setEmail("testuser@example.com");
    }

    @Test
    void create_UserExists_ThrowsException() {
        when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.of(user));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.create(user);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains("is already exist"));

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void create_NewUser_SavesUser() {
        when(userRepository.findByUserName(user.getUserName())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals("testuser", createdUser.getUserName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getAll_ReturnsPagedUsers() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "userName"));
        Page<User> userPage = new PageImpl<>(List.of(user));

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<User> result = userService.getAll(0, 5, "ASC", "userName");

        assertEquals(1, result.getContent().size());
        assertEquals("testuser", result.getContent().get(0).getUserName());
        verify(userRepository, times(1)).findAll(pageable);
    }

    @Test
    void getById_UserExists_ReturnsUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        User foundUser = userService.getById(1);

        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getUserName());
        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void getById_UserNotFound_ThrowsException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.getById(1);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("not found"));

        verify(userRepository, times(1)).findById(1);
    }

    @Test
    void deleteById_UserExists_DeletesUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        userService.deleteById(1);

        verify(userRepository, times(1)).deleteById(1);
    }

    @Test
    void updateById_UserExists_UpdatesUser() {
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        User updatedUser = new User();
        updatedUser.setUserName("updateduser");

        User result = userService.updateById(1, updatedUser);

        assertNotNull(result);
        assertEquals("updateduser", result.getUserName());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateById_UserNotFound_ThrowsException() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            userService.updateById(1, user);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("not found"));

        verify(userRepository, times(1)).findById(1);
    }
}