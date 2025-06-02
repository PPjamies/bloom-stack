package org.bloom.authservice.repository.jpa;

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
@Table(name = DBAppUser.TABLE_NAME)
public class DBAppUser {
    public static final String TABLE_NAME = "app_users";
    public static final String ID = "id";

    // spring security details
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String IS_ENABLED = "isEnabled";
    public static final String IS_ACCOUNT_NON_LOCKED = "isAccountNonLocked";
    public static final String IS_ACCOUNT_NON_EXPIRED = "isAccountNonExpired";
    public static final String IS_CREDENTIALS_NON_EXPIRED = "isCredentialsNonExpired";

    // app owned details

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private Long id;

    @Column(name = USERNAME, nullable = false, unique = true)
    private String username;

    @Column(name = PASSWORD, nullable = false)
    private String password;

    @Column(name = IS_ENABLED, nullable = false)
    @Builder.Default
    private boolean isEnabled = true;

    @Column(name = IS_ACCOUNT_NON_LOCKED, nullable = false)
    @Builder.Default
    private boolean isAccountNonLocked = true;

    @Column(name = IS_ACCOUNT_NON_EXPIRED, nullable = false)
    @Builder.Default
    private boolean isAccountNonExpired = true;

    @Column(name = IS_CREDENTIALS_NON_EXPIRED, nullable = false)
    @Builder.Default
    private boolean isCredentialsNonExpired = true;
}
