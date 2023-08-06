package com.be05.sns.token;

import com.be05.sns.dto.UserDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Date;
import java.time.Instant;

@Component
public class JwtUtils {
    private final Key signingKey;
    private final JwtParser jwtParser;

    public JwtUtils(@Value("${jwt.secret}") String jwtSecret) {
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        this.jwtParser = Jwts
                .parserBuilder()
                .setSigningKey(this.signingKey)
                .build();
    }

    // JWT 생성
    public String generateToken(UserDto userDto) {
        Claims jwtClaims = Jwts.claims()
                .setSubject(userDto.getUsername())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(3600))); // 1시간 후 만료

        return Jwts.builder()
                .setClaims(jwtClaims)
                .signWith(signingKey)
                .compact();
    }

    // JWT 유효성 검사
    public boolean validate(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // JWT 토큰 Claims 파싱 후 반환
    public Claims parseClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }
}
