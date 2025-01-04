package com.app.simpleblogsystem.repository;

import com.app.simpleblogsystem.models.Role;
import com.app.simpleblogsystem.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleName(RoleName name);
}
