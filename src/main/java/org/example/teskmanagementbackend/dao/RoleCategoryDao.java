package org.example.teskmanagementbackend.dao;

import org.example.teskmanagementbackend.entity.RoleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleCategoryDao extends JpaRepository<RoleCategory,Long> {
    RoleCategory findByCode(String code);
}
