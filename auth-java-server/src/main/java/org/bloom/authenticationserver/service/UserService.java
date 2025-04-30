package org.bloom.authenticationserver.service;

import lombok.RequiredArgsConstructor;
import org.bloom.authenticationserver.constant.AuthConstants;
import org.bloom.authenticationserver.converter.AuthConverter;
import org.bloom.authenticationserver.dto.User;
import org.bloom.authenticationserver.exception.AuthException;
import org.bloom.authenticationserver.repository.UserRepository;
import org.bloom.authenticationserver.repository.jpa.DBUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new AuthException(AuthConstants.USER_ALREADY_EXISTS, "User " + username + " already exists");
        }

        DBUser dbUser = userRepository.save(DBUser.builder()
                .username(username)
                .password(password)
                .build());

        return AuthConverter.convert(dbUser);
    }

    public User getUserByUsername(String username) {
        DBUser dbUser = userRepository.findByUsername(username);
        if (dbUser == null) {
            throw new AuthException(AuthConstants.USER_NOT_FOUND, "User " + username + " not found");
        }

        return AuthConverter.convert(dbUser);
    }
}

