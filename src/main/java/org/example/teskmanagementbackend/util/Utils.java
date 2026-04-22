package org.example.teskmanagementbackend.util;

import org.example.teskmanagementbackend.dto.RoleCategoryDto;
import org.example.teskmanagementbackend.dto.RoleDto;
import org.example.teskmanagementbackend.dto.UserDto;
import org.example.teskmanagementbackend.entity.Firm;
import org.example.teskmanagementbackend.entity.Role;
import org.example.teskmanagementbackend.entity.RoleCategory;
import org.example.teskmanagementbackend.entity.User;

import java.util.Set;
import java.util.stream.Collectors;

public class Utils {

    private static Role role;
    private static Firm firm;

    private static RoleCategoryDto toRolecategoryDto(RoleCategory roleCategory) {
        return new RoleCategoryDto(
                roleCategory.getCode(),
                roleCategory.getDescription());
    }

    public static RoleDto roleToDto(Role role) {
        return new RoleDto(
                role.getRoleName(),
                role.getAuthority());
    }

    public static UserDto userToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        Set<RoleDto> roleDto = user.getRoles()
                .stream()
                .map(role -> {
                    RoleDto dto = new RoleDto();
                    dto.setId(role.getId());
                    dto.setRoleName(role.getRoleName());
                    dto.setAuthority(role.getAuthority());
                    return dto;
                })
                .collect(Collectors.toSet());
        userDto.setRole(roleDto);

        return userDto;
    }
}
