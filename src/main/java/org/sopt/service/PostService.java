package org.sopt.service;

import org.sopt.domain.Member;
import org.sopt.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import org.sopt.domain.Post;
import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.PostPageResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;
import org.sopt.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public PostService(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    public PostResponse createPost(CreatePostRequest request) {
        Member member = memberRepository.findById(request.memberId())
                .orElseThrow(() -> new BusinessException(ErrorCode.MEMBER_NOT_FOUND));
        Post post = new Post(request.title(), request.content(), request.category(), request.tags(), member);
        return PostResponse.from(postRepository.save(post));
    }

    public PostPageResponse getPosts(int page, int size) {
        if (page < 0 || size < 1 || size > 100) {
            throw new BusinessException(ErrorCode.INVALID_PAGE);
        }
        Page<Post> postPage = postRepository.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        List<PostResponse> posts = postPage.getContent().stream()
                .map(PostResponse::from)
                .toList();
        return PostPageResponse.of(posts, page, size, postPage.getTotalElements());
    }
    @Transactional
    public PostResponse getPostAndIncreaseViewCount(Long id) {
        Post post = findPost(id);
        post.increaseViewCount();
        return PostResponse.from(post);
    }
    @Transactional
    public PostResponse updatePost(Long id, UpdatePostRequest request) {
        Post post = findPost(id);
        post.update(request.title(), request.content(), request.category(), request.tags());
        return PostResponse.from(post);
    }

    public void deletePost(Long id) {
        Post post = findPost(id);
        postRepository.delete(post);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
    }
}