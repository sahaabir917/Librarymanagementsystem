package com.infinitycodehubltd.librarymanagement.jwt;



import com.infinitycodehubltd.librarymanagement.user.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {
    private static final String SECRET_KEY = "thisisaverysecurekeyforjwttokengeneration12345";

    public String generateToken(Member member) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", member.getRole().name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(member.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(getKey())
                .compact();
    }

    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }

    public String extractRole(String token) {
        return Jwts.parserBuilder().setSigningKey(getKey()).build()
                .parseClaimsJws(token)
                .getBody().get("role", String.class);
    }

    public boolean isTokenValid(String token, Member member) {
        return extractEmail(token).equals(member.getEmail());
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}

