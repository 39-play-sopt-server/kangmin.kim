package org.sopt.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.dto.request.CreateMemberRequest;
import org.sopt.dto.response.MemberResponse;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.response.SuccessCode;
import org.sopt.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(@RequestBody CreateMemberRequest request) {
        return ResponseEntity.status(SuccessCode.MEMBER_CREATED.getStatus())
                .body(ApiResponse.success(SuccessCode.MEMBER_CREATED, memberService.createMember(request)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<Long>> me(@AuthenticationPrincipal Long memberId) {
        return ResponseEntity.status(SuccessCode.MEMBER_FOUND.getStatus())
                .body(ApiResponse.success(SuccessCode.MEMBER_FOUND, memberId));
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteMe(@AuthenticationPrincipal Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.status(SuccessCode.MEMBER_DELETED.getStatus())
                .body(ApiResponse.success(SuccessCode.MEMBER_DELETED));
    }
}