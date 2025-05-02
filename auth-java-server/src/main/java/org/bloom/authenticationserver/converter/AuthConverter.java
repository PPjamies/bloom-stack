package org.bloom.authenticationserver.converter;

import org.bloom.authenticationserver.dto.User;
import org.bloom.authenticationserver.repository.jpa.DBUser;

public class AuthConverter {

    public static User convert(DBUser from) {
        User to = new User();
        to.setUsername(from.getUsername());
        to.setPassword(from.getPassword());
        return to;
    }

    public static DBUser convert(User from) {
        DBUser to = new DBUser();
        to.setUsername(from.getUsername());
        to.setPassword(from.getPassword());
        return to;
    }
}
