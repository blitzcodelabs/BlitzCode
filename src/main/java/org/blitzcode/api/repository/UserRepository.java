package org.blitzcode.api.repository;

import org.blitzcode.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface ot interact with the User table. Users can be found by their ID and email.
 * existsByEmail() can be used to check if a user already exists in the database.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String name);
    boolean existsByEmail(String email);
    User findById(String id);
}