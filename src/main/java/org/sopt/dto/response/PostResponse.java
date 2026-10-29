package org.sopt.dto.response;

import org.sopt.domain.Category;
import org.sopt.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(
        Long id,
        String title,
        String content,
        Category category,
        List<String> tags,
        String author,
        LocalDateTime createdAt,
        int viewCount
) {
    public static PostResponse from(Post post) {
        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCategory(),
                post.getTags(),
                post.getMember().getName(),
                post.getCreatedAt(),
                post.getViewCount()
        );
    }
}