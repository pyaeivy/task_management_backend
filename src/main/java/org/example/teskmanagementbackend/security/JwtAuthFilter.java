package org.example.teskmanagementbackend.security;

import java.io.IOException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Override
	protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
		return request.getRequestURI().startsWith("/task/auth/login");
	}

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		String auth = request.getHeader("Authorization");
		if (auth != null && auth.startsWith("Bearer ")) {

			String token = auth.substring(7);

			Claims claims = jwtUtil.parseToken(token);

			String username = claims.getSubject();
			@SuppressWarnings("unchecked")
			List<String> roles = claims.get("roles", List.class);

			List<SimpleGrantedAuthority> authorities = roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r))
					.toList();

			Authentication authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		filterChain.doFilter(request, response);

	}

}
