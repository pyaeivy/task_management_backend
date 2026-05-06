package org.example.teskmanagementbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.teskmanagementbackend.dto.UserDto;
import org.example.teskmanagementbackend.service.AdminService;
import org.example.teskmanagementbackend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final AuthService authService;

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/create-role")
    public ResponseEntity<?> createRole(
            @RequestBody AdminService.RoleCreate roleCreate) {
        adminService.createRoles(roleCreate);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/fetch-user")
    public ResponseEntity<UserDto> getUserByUsername() {
        UserDto userDto = authService.getUserByUsername();
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/fetch-all-user")
    public ResponseEntity<List<UserDto>> getAllUser() {
        List<UserDto> userDto = authService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

}
