package org.bloom.authservice.service;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.bloom.authservice.constant.AuthError;
import org.bloom.authservice.converter.AuthConverter;
import org.bloom.authservice.dto.AppUser;
import org.bloom.authservice.exception.AuthException;
import org.bloom.authservice.repository.AppUserRepository;
import org.bloom.authservice.repository.jpa.DBAppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;

    // spring security
    @Override
    public UserDetails loadUserByUsername(@NotNull String username) throws UsernameNotFoundException {
        checkAppUserExistence(username, true);
        DBAppUser dbAppUser = appUserRepository.findByUsername(username);
        return AuthConverter.convert(dbAppUser);
    }

    private void checkAppUserExistence(@NotNull String username, boolean shouldExist) {
        boolean userExists = appUserRepository.existsByUsername(username);
        if (shouldExist && !userExists) {
            throw new AuthException(AuthError.APP_USER_NOT_FOUND, "User not found: " + username);
        } else if (!shouldExist && userExists) {
            throw new AuthException(AuthError.APP_USER_ALREADY_EXISTS, "User " + username + " already exists");
        }
    }

    private AppUser saveAppUser(@NotNull AppUser appUser) {
        DBAppUser dbAppUser = appUserRepository.save(AuthConverter.convert(appUser));
        return AuthConverter.convert(dbAppUser);
    }

    public AppUser createAppUser(@NotNull String username, @NotNull String password) {
        checkAppUserExistence(username, false);
        AppUser appUser = AppUser.builder().username(username).password(passwordEncoder.encode(password)).build();
        return saveAppUser(appUser);
    }
}