package org.example.teskmanagementbackend.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.example.teskmanagementbackend.dto.RegisterDto;
import org.example.teskmanagementbackend.dto.UserDto;
import org.example.teskmanagementbackend.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController()
@RequestMapping("/task/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterDto> register(@RequestBody RegisterDto registerDto){
       RegisterDto response = authService.register(registerDto);
       return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    @PostMapping("/login")
    public ResponseEntity<AuthService.LoginResponse> login(@RequestBody AuthService.LoginRequest request){
        AuthService.LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/user")
    public ResponseEntity<UserDto> getUserByUsername(){
    	return ResponseEntity.ok(authService.getUserByUsername());
    }
    
    @GetMapping("/all-user")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUser(){
    	return ResponseEntity.ok(authService.getAllUser());
    }
}
