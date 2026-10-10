package org.sopt.service;

import org.sopt.domain.Category;
import org.sopt.domain.Post;
import org.sopt.exception.PostNotFoundException;
import org.sopt.repository.PostRepository;

import java.util.List;

public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(String title, String content, Category category, List<String> tags, String author) {
        postRepository.save(new Post(title, content, category, tags, author));
    }

    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPost(int index) {
        Post post = findPost(index);
        post.increaseViewCount();
        return post;
    }

    public void updatePost(int index, String title, String content, Category category, List<String> tags) {
        findPost(index).update(title, content, category, tags);
    }

    public void deletePost(int index) {
        postRepository.delete(findPost(index));
    }

    private Post findPost(int index) {
        return postRepository.findByIndex(index)
                .orElseThrow(() -> new PostNotFoundException("존재하지 않는 게시글입니다."));
    }
}