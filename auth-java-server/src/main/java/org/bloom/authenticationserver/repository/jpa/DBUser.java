package org.bloom.authenticationserver.repository.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = DBUser.TABLE_NAME)
public class DBUser {
    public static final String TABLE_NAME = "user";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private long id;

    @Column(name = USERNAME, nullable = false, unique = true)
    private String username;

    @Column(name = PASSWORD, nullable = false)
    private String password;
}
