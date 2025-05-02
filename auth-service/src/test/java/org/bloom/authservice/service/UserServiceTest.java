package org.bloom.authservice.service;

import org.bloom.authservice.constant.AuthError;
import org.bloom.authservice.dto.User;
import org.bloom.authservice.exception.AuthException;
import org.bloom.authservice.repository.UserRepository;
import org.bloom.authservice.repository.jpa.DBUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_whenUserDoesNotExist() {
        String username = "test-user";
        String password = "test-pass";
        String hashedPassword = "pss-teast";

        DBUser dbUser = DBUser.builder()
                .username(username)
                .password(hashedPassword)
                .build();

        // mock
        when(passwordEncoder.encode(password)).thenReturn(hashedPassword);
        when(userRepository.existsByUsername(username)).thenReturn(false);
        when(userRepository.save(dbUser)).thenReturn(dbUser);

        User user = userService.createUser(username, password);
        assert (user.getUsername().equals(username));
        assert (user.getPassword().equals(hashedPassword));
    }

    @Test
    void createUser_whenUserExists() {
        String username = "test-user";
        String password = "test-pass";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        AuthException exception = assertThrows(AuthException.class, () -> {
            userService.createUser(username, password);
        });

        assertEquals(AuthError.USER_ALREADY_EXISTS, exception.getErrorCode());
        // assertEquals("User test-user already exists", exception.getMessage());
    }

    @Test
    void getUser_whenUserExists() {
        String username = "test-user";
        String hashedPassword = "pss-teast";

        // mock
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(DBUser.builder()
                .username(username)
                .password(hashedPassword)
                .build());

        User user = userService.getUser(username);
        assert (user.getUsername().equals(username));
        assert (user.getPassword().equals(hashedPassword));
    }

    @Test
    void getUser_whenUserDoesNotExist() {
        String username = "test-user";

        when(userRepository.existsByUsername(username)).thenReturn(false);

        AuthException exception = assertThrows(AuthException.class, () -> {
            userService.getUser(username);
        });

        assertEquals(AuthError.USER_NOT_FOUND, exception.getErrorCode());
        // assertEquals("User test-user not found", exception.getMessage());
    }

    @Test
    void getUserAndValidatePassword_whenUserExists() {
        String username = "test-user";
        String hashedPassword = "pss-teast";

        // mock
        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(DBUser.builder()
                .username(username)
                .password(hashedPassword)
                .build());
        when(passwordEncoder.matches(hashedPassword, hashedPassword)).thenReturn(true);

        User user = userService.getUserAndValidatePassword(username, hashedPassword);
        assert (user.getUsername().equals(username));
        assert (user.getPassword().equals(hashedPassword));
    }

    @Test
    void getUserAndValidatePassword_whenPasswordDoesNotMatch() {
        String username = "test-user";
        String hashedPassword = "pss-teast";

        when(userRepository.existsByUsername(username)).thenReturn(true);
        when(userRepository.findByUsername(username)).thenReturn(DBUser.builder()
                .username(username)
                .password(hashedPassword)
                .build());
        when(passwordEncoder.matches(hashedPassword, hashedPassword)).thenReturn(false);

        AuthException exception = assertThrows(AuthException.class, () -> {
            userService.getUserAndValidatePassword(username, hashedPassword);
        });

        assertEquals(AuthError.PASSWORD_MISMATCH, exception.getErrorCode());
        // assertEquals("Password does not match", exception.getMessage());
    }
}