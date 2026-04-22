package org.example.teskmanagementbackend.security;

import java.security.Key;
import java.util.Date;
import java.util.List;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
	}

	public String generateToken(String username, List<String> roles) {
		return Jwts.builder()
				.setSubject(username)
				.claim("roles", roles)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 1000))
				.signWith(getSigningKey()) // ← same key as parseToken
				.compact();
	}

	public Claims parseToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSigningKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
}
