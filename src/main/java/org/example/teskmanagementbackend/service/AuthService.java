package org.example.teskmanagementbackend.service;

import lombok.RequiredArgsConstructor;
import org.example.teskmanagementbackend.dao.RoleDao;
import org.example.teskmanagementbackend.dao.UserDao;
import org.example.teskmanagementbackend.dto.RegisterDto;
import org.example.teskmanagementbackend.dto.UserDto;
import org.example.teskmanagementbackend.entity.Role;
import org.example.teskmanagementbackend.entity.User;
import org.example.teskmanagementbackend.security.JwtUtil;
import org.example.teskmanagementbackend.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder encoder;

    public record LoginRequest(String username, String password) {
    }

    public record LoginResponse(String token) {
    }

    public List<UserDto> getAllUser() {
        return userDao.findAll()
                .stream()
                .map(Utils::userToDto)
                .toList();
    }

    public UserDto getUserByUsername(String username) {
        return userDao.findByUsername(username)
                .stream()
                .map(Utils::userToDto)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public RegisterDto register(RegisterDto registerDto) {
        if (userDao.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already in use");
        }
        User user = new User(
                registerDto.getFirstName(),
                registerDto.getLastName(),
                registerDto.getUsername(),
                encoder.encode(registerDto.getPassword()),
                registerDto.getEmail());
        Role role;
        if (user.getId() == 1) {
            role = roleDao.findByRoleName("SUPER_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Role not found"));
        } else {
            role = roleDao.findByRoleName("USER")
                    .orElseThrow(() -> new RuntimeException("Role not found"));
        }
        user.getRoles().add(role);
        userDao.save(user);
        return registerDto;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userDao.findByUsername(request.username)
                .orElseThrow(() -> new BadCredentialsException("Invalid Credentials"));

        if (!encoder.matches(request.password, user.getPassword())) {
            throw new BadCredentialsException("Invalid Credentials");
        }
        List<String> role = user.getRoles()
                .stream()
                .map(Role::getRoleName)
                .toList();
        String token = jwtUtil.generateToken(user.getUsername(), role);
        return new LoginResponse(token);
    }
}
