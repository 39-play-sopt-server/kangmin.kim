package org.sopt.service;

import org.sopt.domain.Post;
import org.sopt.dto.CreatePostRequest;
import org.sopt.dto.UpdatePostRequest;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(CreatePostRequest request) {
        Post post = new Post(request.title(), request.content(), request.category(), request.tags(), request.author());
        return postRepository.save(post);
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostAndIncreaseViewCount(Long id) {
        Post post = findPost(id);
        post.increaseViewCount();
        return post;
    }

    public Post updatePost(Long id, UpdatePostRequest request) {
        Post post = findPost(id);
        post.update(request.title(), request.content(), request.category(), request.tags());
        return post;
    }

    public void deletePost(Long id) {
        findPost(id);
        postRepository.delete(id);
    }

    private Post findPost(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다."));
    }
}