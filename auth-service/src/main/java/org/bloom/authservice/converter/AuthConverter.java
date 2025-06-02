package org.bloom.authservice.converter;

import org.bloom.authservice.dto.AppUser;
import org.bloom.authservice.repository.jpa.DBAppUser;

public class AuthConverter {

    public static AppUser convert(DBAppUser from) {
        return AppUser.builder()
                .username(from.getUsername())
                .password(from.getPassword())
                .build();
    }

    public static DBAppUser convert(AppUser from) {
        return DBAppUser.builder()
                .username(from.getUsername())
                .password(from.getPassword())
                .build();
    }
}
