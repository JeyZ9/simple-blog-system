package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.models.AuthenticationToken;
import com.app.simpleblogsystem.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findTokenByUser(User user);
    AuthenticationToken findTokenByToken(String token);
}
