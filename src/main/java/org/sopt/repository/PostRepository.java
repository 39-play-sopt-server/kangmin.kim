package org.sopt.repository;

import org.sopt.domain.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostRepository {
    private final List<Post> posts = new ArrayList<>();

    public void save(Post post) {
        posts.add(post);
    }

    public List<Post> findAll() {
        return posts;
    }

    public Optional<Post> findByIndex(int index) {
        if (index < 0 || index >= posts.size()) {
            return Optional.empty();
        }
        return Optional.of(posts.get(index));
    }

    public void delete(Post post) {
        posts.remove(post);
    }
}