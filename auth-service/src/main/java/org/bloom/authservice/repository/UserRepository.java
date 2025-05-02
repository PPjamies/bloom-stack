package org.bloom.authservice.repository;

import org.bloom.authservice.repository.jpa.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<DBUser, Long> {
    boolean existsByUsername(String username);
    DBUser findByUsername(String username);
}
