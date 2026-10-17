package org.sopt.dto.response;

import java.util.List;

public record PostPageResponse(
        List<PostResponse> posts,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean hasNext
) {
    public static PostPageResponse of(List<PostResponse> posts, int page, int size, long totalElements) {
        int totalPages = (int) Math.ceil((double) totalElements / size);
        return new PostPageResponse(posts, page, size, totalElements, totalPages, page + 1 < totalPages);
    }
}