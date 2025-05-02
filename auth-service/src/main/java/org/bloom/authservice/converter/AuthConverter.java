package org.bloom.authservice.converter;

import org.bloom.authservice.dto.User;
import org.bloom.authservice.repository.jpa.DBUser;

public class AuthConverter {

    public static User convert(DBUser from) {
        return User.builder()
                .username(from.getUsername())
                .password(from.getPassword())
                .build();
    }

    public static DBUser convert(User from) {
        return DBUser.builder()
                .username(from.getUsername())
                .password(from.getPassword())
                .build();
    }
}
