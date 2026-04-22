package org.example.teskmanagementbackend.dto;

import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;




}
