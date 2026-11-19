package org.sopt.service;

import lombok.RequiredArgsConstructor;
import org.sopt.domain.Member;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.sopt.repository.MemberRepository;
import org.sopt.repository.PostRepository;
import org.sopt.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        postRepository.deleteAllByMember(member);
        refreshTokenRepository.deleteByMemberId(memberId);
        memberRepository.delete(member);
    }
}