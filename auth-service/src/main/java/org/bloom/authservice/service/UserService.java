package org.bloom.authservice.service;

import lombok.RequiredArgsConstructor;
import org.bloom.authservice.constant.AuthError;
import org.bloom.authservice.converter.AuthConverter;
import org.bloom.authservice.dto.User;
import org.bloom.authservice.exception.AuthException;
import org.bloom.authservice.repository.UserRepository;
import org.bloom.authservice.repository.jpa.DBUser;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public boolean isExistingUser(String username) {
        return userRepository.existsByUsername(username);
    }

    public User createUser(String username, String password) {
        if (isExistingUser(username)) {
            throw new AuthException(AuthError.USER_ALREADY_EXISTS, "User " + username + " already exists");
        }
        return saveUser(username, password);
    }

    private User saveUser(String username, String password) {
        DBUser dbUser = userRepository.save(DBUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password)) // hash password before saving
                .build());
        return AuthConverter.convert(dbUser);
    }

    public User getUser(String username) {
        if (!isExistingUser(username)) {
            throw new AuthException(AuthError.USER_NOT_FOUND, "User " + username + " not found");
        }
        DBUser dbUser = userRepository.findByUsername(username);
        return AuthConverter.convert(dbUser);
    }

    public User getUserAndValidatePassword(String username, String password) {
        User user = getUser(username);
        if (!passwordEncoder.matches(user.getPassword(), password)) {
            throw new AuthException(AuthError.PASSWORD_MISMATCH, "Password does not match");
        }
        return user;
    }
}

