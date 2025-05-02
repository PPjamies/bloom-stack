package org.bloom.authenticationserver.service;

import lombok.RequiredArgsConstructor;
import org.bloom.authenticationserver.constant.AuthError;
import org.bloom.authenticationserver.converter.AuthConverter;
import org.bloom.authenticationserver.dto.User;
import org.bloom.authenticationserver.exception.AuthException;
import org.bloom.authenticationserver.repository.UserRepository;
import org.bloom.authenticationserver.repository.jpa.DBUser;
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

    private User saveUser(String username, String password) {
        // hash password before saving into db
        password = passwordEncoder.encode(password);
        DBUser dbUser = userRepository.save(DBUser.builder()
                .username(username)
                .password(password)
                .build());
        return AuthConverter.convert(dbUser);
    }

    public User createUser(String username, String password) {
        if (isExistingUser(username)) {
            throw new AuthException(AuthError.USER_ALREADY_EXISTS, "User " + username + " already exists");
        }
        return saveUser(username, password);
    }

    public User updateUser(String username, String password) {
        if (!isExistingUser(username)) {
            throw new AuthException(AuthError.USER_NOT_FOUND, "User " + username + " not found");
        }
        return saveUser(username, password);
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

