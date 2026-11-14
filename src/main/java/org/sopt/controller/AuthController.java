package org.sopt.controller;

import org.sopt.dto.request.LoginRequest;
import org.sopt.dto.request.SignupRequest;
import org.sopt.dto.response.LoginResponse;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.response.SuccessCode;
import org.sopt.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Long>> signup(@RequestBody SignupRequest request) {
        Long memberId = authService.signup(request.email(), request.password(), request.name());
        return ResponseEntity.status(SuccessCode.SIGNUP_SUCCESS.getStatus())
                .body(ApiResponse.success(SuccessCode.SIGNUP_SUCCESS, memberId));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        String accessToken = authService.login(request.email(), request.password());
        return ResponseEntity.status(SuccessCode.LOGIN_SUCCESS.getStatus())
                .body(ApiResponse.success(SuccessCode.LOGIN_SUCCESS, new LoginResponse(accessToken)));
    }
}