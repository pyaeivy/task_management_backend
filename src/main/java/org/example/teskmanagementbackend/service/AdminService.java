package org.example.teskmanagementbackend.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.teskmanagementbackend.dao.RoleDao;
import org.example.teskmanagementbackend.dto.RoleCategoryDto;
import org.example.teskmanagementbackend.dto.RoleDto;
import org.example.teskmanagementbackend.entity.Role;
import org.example.teskmanagementbackend.entity.RoleCategory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final RoleDao roleDao;

    public record RoleCreate(RoleDto roleDto,RoleCategoryDto roleCategoryDto){}

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Transactional
    public void createRoles(RoleCreate request){
        if(roleDao.existsByRoleName(request.roleDto.getRoleName())){
            throw new IllegalStateException("Role already exists");
        }

        RoleCategory category = new RoleCategory(
                request.roleCategoryDto().getCode(),
                request.roleCategoryDto().getDescription()
        );

        Role role = new Role(
                request.roleDto().getRoleName(),
                request.roleDto().getAuthority()
        );

        role.setRoleCategory(category);
        roleDao.save(role);

    }
}
