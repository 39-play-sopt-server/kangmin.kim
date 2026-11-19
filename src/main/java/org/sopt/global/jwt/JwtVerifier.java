package org.sopt.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@RequiredArgsConstructor
public class JwtVerifier {

    private final SecretKey secretKey;

    public Long getMemberId(String token, TokenType expectedType) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        if (!expectedType.name().equals(claims.get(TokenType.CLAIM_NAME, String.class))) {
            throw new JwtException("토큰 타입이 올바르지 않습니다.");
        }
        return Long.valueOf(claims.getSubject());
    }
}