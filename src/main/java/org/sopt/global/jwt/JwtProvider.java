package org.sopt.global.jwt;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final SecretKey secretKey;
    private final JwtProperties jwtProperties;

    public String createAccessToken(Long memberId) {
        return createToken(memberId, TokenType.ACCESS);
    }

    public String createRefreshToken(Long memberId) {
        return createToken(memberId, TokenType.REFRESH);
    }

    private String createToken(Long memberId, TokenType type) {
        Date now = new Date();

        return Jwts.builder()
                .subject(String.valueOf(memberId))
                .claim(TokenType.CLAIM_NAME, type.name())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + jwtProperties.expirationOf(type)))
                .signWith(secretKey)
                .compact();
    }
}