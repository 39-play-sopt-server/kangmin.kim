package org.sopt.controller;

import org.sopt.dto.request.CreateMemberRequest;
import org.sopt.dto.response.MemberResponse;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.response.SuccessCode;
import org.sopt.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponse>> createMember(@RequestBody CreateMemberRequest request) {
        return ResponseEntity.status(SuccessCode.MEMBER_CREATED.getStatus())
                .body(ApiResponse.success(SuccessCode.MEMBER_CREATED, memberService.createMember(request)));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<ApiResponse<Void>> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.status(SuccessCode.MEMBER_DELETED.getStatus())
                .body(ApiResponse.success(SuccessCode.MEMBER_DELETED));
    }
}