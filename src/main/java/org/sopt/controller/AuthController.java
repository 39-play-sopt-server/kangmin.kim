package org.sopt.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.dto.request.LoginRequest;
import org.sopt.dto.request.SignupRequest;
import org.sopt.dto.response.TokenResponse;
import org.sopt.global.jwt.JwtProperties;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.response.SuccessCode;
import org.sopt.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String REFRESH_TOKEN_COOKIE = "refreshToken";

    private final AuthService authService;
    private final JwtProperties jwtProperties;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Long>> signup(@RequestBody SignupRequest request) {
        Long memberId = authService.signup(request.email(), request.password(), request.name());
        return ResponseEntity.status(SuccessCode.SIGNUP_SUCCESS.getStatus())
                .body(ApiResponse.success(SuccessCode.SIGNUP_SUCCESS, memberId));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody LoginRequest request) {
        TokenResponse tokens = authService.login(request.email(), request.password());
        return toTokenResponse(SuccessCode.LOGIN_SUCCESS, tokens);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ApiResponse<Void>> reissue(
            @CookieValue(name = REFRESH_TOKEN_COOKIE, required = false) String refreshToken) {
        TokenResponse tokens = authService.reissue(refreshToken);
        return toTokenResponse(SuccessCode.TOKEN_REISSUED, tokens);
    }

    private ResponseEntity<ApiResponse<Void>> toTokenResponse(SuccessCode code, TokenResponse tokens) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE, tokens.refreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/api/v1/auth")
                .maxAge(Duration.ofMillis(jwtProperties.refreshTokenExpiration()))
                .build();

        return ResponseEntity.status(code.getStatus())
                .header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + tokens.accessToken())
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success(code));
    }
}