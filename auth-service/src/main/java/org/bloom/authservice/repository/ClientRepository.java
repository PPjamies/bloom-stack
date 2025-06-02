package org.bloom.authservice.repository;

import org.bloom.authservice.repository.jpa.DBClient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<DBClient, Long> {
}
