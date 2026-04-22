package org.example.teskmanagementbackend.roleRsp;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.teskmanagementbackend.dao.RoleCategoryDao;
import org.example.teskmanagementbackend.dao.RoleDao;
import org.example.teskmanagementbackend.entity.Role;
import org.example.teskmanagementbackend.entity.RoleCategory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleBootstrap {

    private final RoleDao roleDao;
    private final RoleCategoryDao roleCategoryDao;

    @PostConstruct
    public void init(){
        if(roleCategoryDao.findByCode("SYSTEM") == null) {
            RoleCategory category = new RoleCategory();
            category.setCode("SYSTEM");
            category.setDescription("SYSTEM");
            roleCategoryDao.save(category);


            if (!roleDao.existsByRoleName("SUPER_ADMIN")) {
                Role role = new Role();
                role.setRoleName("SUPER_ADMIN");
                role.setAuthority("ROLE_SUPER_ADMIN");
                role.setRoleCategory(category);
                roleDao.save(role);
            }

            if (!roleDao.existsByRoleName("USER")) {
                Role role = new Role();
                role.setRoleName("USER");
                role.setAuthority("ROLE_USER");
                role.setRoleCategory(category);
                roleDao.save(role);
            }
        }
    }

}
