package org.sopt.repository;

import org.sopt.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepository {
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    public Post save(Post post) {
        post.assignId(sequence.incrementAndGet());
        posts.put(post.getId(), post);
        return post;
    }

    public List<Post> findPage(int page, int size) {
        return posts.values().stream()
                .sorted(Comparator.comparing(Post::getId).reversed())
                .skip((long) page * size)
                .limit(size)
                .toList();
    }

    public long count() {
        return posts.size();
    }

    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public void delete(Long id) {
        posts.remove(id);
    }
}