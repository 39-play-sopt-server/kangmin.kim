package org.sopt.service;

import org.sopt.domain.Member;
import org.sopt.dto.request.CreateMemberRequest;
import org.sopt.dto.response.MemberResponse;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.sopt.repository.MemberRepository;
import org.sopt.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public MemberService(MemberRepository memberRepository, PostRepository postRepository) {
        this.memberRepository = memberRepository;
        this.postRepository = postRepository;
    }

    public MemberResponse createMember(CreateMemberRequest request) {
        Member member = new Member(request.name());
        return MemberResponse.from(memberRepository.save(member));
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        postRepository.deleteAllByMember(member);
        memberRepository.delete(member);
    }
}