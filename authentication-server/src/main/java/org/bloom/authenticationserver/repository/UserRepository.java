package org.bloom.authenticationserver.repository;

import org.bloom.authenticationserver.repository.jpa.DBUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<DBUser, Long> {
    DBUser findByUsername(String username);
}
