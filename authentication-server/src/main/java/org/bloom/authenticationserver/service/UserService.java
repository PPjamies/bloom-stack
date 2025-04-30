package org.bloom.authenticationserver.service;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.bloom.authenticationserver.converter.AuthenticationConverter;
import org.bloom.authenticationserver.dto.User;
import org.bloom.authenticationserver.repository.UserRepository;
import org.bloom.authenticationserver.repository.jpa.DBUser;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User saveUser(String username, String password) {
        try {
            DBUser dbUser = userRepository.save(DBUser.builder()
                    .username(username)
                    .password(password)
                    .build());
            return AuthenticationConverter.convert(dbUser);
        } catch (Exception e) {
            return null; //todo: handle exception
        }
    }

    public User getUserByUsername(String username) {
        if (StringUtils.isEmpty(username))
            return null;

        try {
            return AuthenticationConverter.convert(userRepository.findByUsername(username));
        } catch (Exception e) {
            return null; //todo: handle exception
        }
    }
}

