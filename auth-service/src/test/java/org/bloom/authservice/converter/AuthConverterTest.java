package org.bloom.authservice.converter;

import com.google.gson.Gson;
import org.bloom.authservice.dto.User;
import org.bloom.authservice.repository.jpa.DBUser;
import org.junit.jupiter.api.Test;

public class AuthConverterTest {

    private final Gson gson = new Gson();

    @Test
    void userConverterTest() {
        String username = "username";
        String password = "password";

        User expectedUser = User.builder()
                .username(username)
                .password(password)
                .build();

        DBUser expectedDBUser = DBUser.builder()
                .username(username)
                .password(password)
                .build();

        User actualUser = AuthConverter.convert(expectedDBUser);
        assert (gson.toJson(expectedUser).equals(gson.toJson(actualUser)));

        DBUser actualDBUser = AuthConverter.convert(expectedUser);
        assert (gson.toJson(expectedDBUser).equals(gson.toJson(actualDBUser)));
    }
}
