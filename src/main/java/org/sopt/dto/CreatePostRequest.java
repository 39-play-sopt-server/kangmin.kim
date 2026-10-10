package org.sopt.dto;

import org.sopt.domain.Category;

import java.util.List;

public record CreatePostRequest(
        String title,
        String content,
        Category category,
        List<String> tags,
        String author
) {
}