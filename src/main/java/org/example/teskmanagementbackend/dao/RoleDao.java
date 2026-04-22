package org.example.teskmanagementbackend.dao;

import org.example.teskmanagementbackend.entity.Role;
import org.example.teskmanagementbackend.entity.RoleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleDao extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String roleName);

    boolean existsByRoleName(String roleName);

    Role findByRoleNameAndRoleCategory(String roleName, RoleCategory roleCategory);
}
