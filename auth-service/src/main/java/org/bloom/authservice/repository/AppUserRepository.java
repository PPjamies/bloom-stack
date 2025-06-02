package org.bloom.authservice.repository;

import org.bloom.authservice.repository.jpa.DBAppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<DBAppUser, Long> {
    boolean existsByUsername(String username);
    DBAppUser findByUsername(String username);
}
