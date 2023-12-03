package org.blitzcode.api.repository;

import org.blitzcode.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String name);
    boolean existsByEmail(String email);

    User findById(String id);

}