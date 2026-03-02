////package com.infinitycodehubltd.librarymanagement.jwt;
////
////
////
////import com.infinitycodehubltd.librarymanagement.user.Member;
////import io.jsonwebtoken.*;
////import io.jsonwebtoken.security.Keys;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.stereotype.Service;
////
////import java.nio.charset.StandardCharsets;
////import java.security.Key;
////import java.util.Date;
////import java.util.HashMap;
////
////@Service
////public class JwtService {
////
////    @Value("${app.jwtSecret:thisisaverysecurekeyforjwttokengeneration12345}")
////    private String secretKey;
////
////    @Value("${app.jwtExpirationMs:3600000}")
////    private long jwtExpirationMs;
////
////    @Value("${app.jwtRefreshExpirationMs:86400000}")
////    private long jwtRefreshExpirationMs;
////
////    @Value("${app.jwtClockSkewSeconds:60}")
////    private long jwtClockSkewSeconds;
////
////    public String generateToken(Member member) {
////        HashMap<String, Object> claims = new HashMap<>();
////        claims.put("role", member.getRole().name());
////        claims.put("type", "access");
////
////        return Jwts.builder()
////                .setClaims(claims)
////                .setSubject(member.getEmail())
////                .setIssuedAt(new Date())
////                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
////                .signWith(getKey())
////                .compact();
////    }
////
////    public String generateRefreshToken(Member member) {
////        HashMap<String, Object> claims = new HashMap<>();
////        claims.put("role", member.getRole().name());
////        claims.put("type", "refresh");
////
////        return Jwts.builder()
////                .setClaims(claims)
////                .setSubject(member.getEmail())
////                .setIssuedAt(new Date())
////                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpirationMs))
////                .signWith(getKey())
////                .compact();
////    }
////
////    public String extractEmail(String token) {
////        return extractAllClaims(token).getSubject();
////    }
////
////    public String extractRole(String token) {
////        return extractAllClaims(token).get("role", String.class);
////    }
////
////    public boolean isTokenValid(String token, Member member) {
////        return extractEmail(token).equals(member.getEmail()) && !isTokenExpired(token);
////    }
////
////    public boolean isRefreshToken(String token) {
////        return "refresh".equals(extractAllClaims(token).get("type", String.class));
////    }
////
////    private boolean isTokenExpired(String token) {
////        return extractAllClaims(token).getExpiration().before(new Date());
////    }
////
////    private Claims extractAllClaims(String token) {
////        return Jwts.parserBuilder()
////                .setSigningKey(getKey())
////                .setAllowedClockSkewSeconds(jwtClockSkewSeconds)
////                .build()
////                .parseClaimsJws(token)
////                .getBody();
////    }
////
////    private Key getKey() {
////        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
////    }
////}
////
//
//package com.infinitycodehubltd.librarymanagement.jwt;
//
//import com.infinitycodehubltd.librarymanagement.user.Member;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.HashMap;
//
//@Service
//public class JwtService {
//
//    @Value("${app.jwtSecretBase64}")
//    private String secretKeyBase64;
//
//    @Value("${app.jwtExpirationMs:3600000}")
//    private long jwtExpirationMs;
//
//    @Value("${app.jwtRefreshExpirationMs:86400000}")
//    private long jwtRefreshExpirationMs;
//
//    @Value("${app.jwtClockSkewSeconds:60}")
//    private long jwtClockSkewSeconds;
//
//    public String generateToken(Member member) {
//        HashMap<String, Object> claims = new HashMap<>();
//        claims.put("role", member.getRole().name());
//        claims.put("type", "access");
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(member.getEmail())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//                .signWith(getKey())
//                .compact();
//    }
//
//    public String generateRefreshToken(Member member) {
//        HashMap<String, Object> claims = new HashMap<>();
//        claims.put("role", member.getRole().name());
//        claims.put("type", "refresh");
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(member.getEmail())
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + jwtRefreshExpirationMs))
//                .signWith(getKey())
//                .compact();
//    }
//
//    public String extractEmail(String token) {
//        return extractAllClaims(token).getSubject();
//    }
//
//    public String extractRole(String token) {
//        return extractAllClaims(token).get("role", String.class);
//    }
//
//    public boolean isTokenValid(String token, Member member) {
//        return extractEmail(token).equals(member.getEmail()) && !isTokenExpired(token);
//    }
//
//    public boolean isRefreshToken(String token) {
//        return "refresh".equals(extractAllClaims(token).get("type", String.class));
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractAllClaims(token).getExpiration().before(new Date());
//    }
//
//    private Claims extractAllClaims(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(getKey())
//                .setAllowedClockSkewSeconds(jwtClockSkewSeconds)
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Key getKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKeyBase64);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }
//}


package com.infinitycodehubltd.librarymanagement.jwt;

import com.infinitycodehubltd.librarymanagement.user.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    // Use Base64 secret from application.properties / Railway Variables
    @Value("${app.jwtSecretBase64}")
    private String secretKeyBase64;

    @Value("${app.jwtExpirationMs:3600000}")
    private long jwtExpirationMs;

    @Value("${app.jwtRefreshExpirationMs:86400000}")
    private long jwtRefreshExpirationMs;

    @Value("${app.jwtClockSkewSeconds:60}")
    private long jwtClockSkewSeconds;

    public String generateToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", member.getRole().name());
        claims.put("type", "access");

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(member.getEmail())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtExpirationMs))
                .signWith(getKey())
                .compact();
    }

    public String generateRefreshToken(Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", member.getRole().name());
        claims.put("type", "refresh");

        long now = System.currentTimeMillis();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(member.getEmail())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtRefreshExpirationMs))
                .signWith(getKey())
                .compact();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token, Member member) {
        return extractEmail(token).equals(member.getEmail()) && !isTokenExpired(token);
    }

    public boolean isRefreshToken(String token) {
        return "refresh".equals(extractAllClaims(token).get("type", String.class));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .setAllowedClockSkewSeconds(jwtClockSkewSeconds)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getKey() {
        // Base64 -> bytes -> HMAC-SHA key (must be >= 256 bits)
        byte[] keyBytes = Decoders.BASE64.decode(secretKeyBase64);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}