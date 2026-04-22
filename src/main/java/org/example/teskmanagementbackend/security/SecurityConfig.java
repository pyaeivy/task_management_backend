package org.example.teskmanagementbackend.security;

import org.example.teskmanagementbackend.roleRsp.RolesHierarchyBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public RoleHierarchy roleHierachy(){
        return RoleHierarchyImpl.fromHierarchy(
                new RolesHierarchyBuilder()
                        .append("ROLE_SUPER_ADMIN", "ROLE_EMPLOYEE_ADMIN")
                        .append("ROLE_EMPLOYEE_ADMIN", "ROLE_EMPLOYEE_READ")
                        .append("ROLE_EMPLOYEE_ADMIN", "ROLE_EMPLOYEE_CREATE")
                        .append("ROLE_EMPLOYEE_ADMIN", "ROLE_EMPLOYEE_DELETE")
                        .append("ROLE_EMPLOYEE_ADMIN", "ROLE_EMPLOYEE_UPDATE")
                        .build()
        );
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
     }

     @Bean
     public AuthenticationManager authenticationManager
             (AuthenticationConfiguration config)throws Exception{
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception{

        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        http.authorizeHttpRequests(c ->{
            c.requestMatchers("/task/auth/**").permitAll();
            c.requestMatchers("/admin/**").authenticated();
            c.requestMatchers("/board/**").authenticated();
            c.anyRequest().authenticated();
        });

        http.cors(
                c->{
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedHeaders(List.of("*"));
                        config.setAllowedMethods(List.of("*"));
                        config.setAllowedOrigins(List.of("http://localhost:4200"));
                        config.setAllowCredentials(true);
                        return config;
                    };
                    c.configurationSource(source);
                }

        );
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
}
