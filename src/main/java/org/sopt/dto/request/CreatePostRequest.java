package org.sopt.dto.request;

import org.sopt.domain.Category;

import java.util.List;

public record CreatePostRequest(
        String title,
        String content,
        Category category,
        List<String> tags,
        Long memberId
) {
}