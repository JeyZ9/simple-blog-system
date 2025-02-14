package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUsername(String username);

    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
