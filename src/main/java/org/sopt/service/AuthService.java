package org.sopt.service;

import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.sopt.domain.Member;
import org.sopt.domain.RefreshToken;
import org.sopt.dto.response.TokenResponse;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.sopt.global.jwt.JwtProvider;
import org.sopt.global.jwt.JwtVerifier;
import org.sopt.global.jwt.TokenType;
import org.sopt.repository.MemberRepository;
import org.sopt.repository.RefreshTokenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final JwtVerifier jwtVerifier;

    @Transactional
    public Long signup(String email, String password, String name) {
        if (memberRepository.existsByEmail(email)) {
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        Member member = new Member(email, passwordEncoder.encode(password), name);
        return memberRepository.save(member).getId();
    }

    @Transactional
    public TokenResponse login(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_LOGIN));

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_LOGIN);
        }

        return issueTokens(member.getId());
    }

    @Transactional
    public TokenResponse reissue(String refreshToken) {
        Long memberId = getMemberIdFromRefreshToken(refreshToken);

        RefreshToken savedToken = refreshTokenRepository.findByMemberId(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN));

        if (!savedToken.getToken().equals(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        return issueTokens(memberId);
    }

    private TokenResponse issueTokens(Long memberId) {
        String accessToken = jwtProvider.createAccessToken(memberId);
        String refreshToken = jwtProvider.createRefreshToken(memberId);

        refreshTokenRepository.findByMemberId(memberId)
                .ifPresentOrElse(
                        savedToken -> savedToken.updateToken(refreshToken),
                        () -> refreshTokenRepository.save(new RefreshToken(memberId, refreshToken))
                );

        return new TokenResponse(accessToken, refreshToken);
    }

    private Long getMemberIdFromRefreshToken(String refreshToken) {
        try {
            return jwtVerifier.getMemberId(refreshToken, TokenType.REFRESH);
        } catch (JwtException | IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }
    }
}