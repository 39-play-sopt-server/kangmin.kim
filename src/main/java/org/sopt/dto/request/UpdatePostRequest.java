package org.sopt.dto.request;

import org.sopt.domain.Category;

import java.util.List;

public record UpdatePostRequest(
        String title,
        String content,
        Category category,
        List<String> tags
) {
}