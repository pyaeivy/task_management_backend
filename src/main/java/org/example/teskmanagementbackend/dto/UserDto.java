package org.example.teskmanagementbackend.dto;


import lombok.*;


import java.util.Set;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Set<RoleDto> role;
    private FirmDto firm;





}
