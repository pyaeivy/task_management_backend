package org.example.teskmanagementbackend.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@NoArgsConstructor
public class RoleDto {
    private Long id;
    private String roleName;
    private String authority;

    public RoleDto(String roleName, String authority) {
        this.roleName = roleName;
        this.authority = authority;
    }
}
