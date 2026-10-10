package org.sopt.repository;

import org.sopt.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new HashMap<>();
    private Long sequence = 0L;

    public Post save(Post post) {
        post.assignId(++sequence);
        posts.put(post.getId(), post);
        return post;
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public void delete(Long id) {
        posts.remove(id);
    }
}