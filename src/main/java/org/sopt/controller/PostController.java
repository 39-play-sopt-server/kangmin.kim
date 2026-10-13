package org.sopt.controller;

import org.sopt.dto.request.CreatePostRequest;
import org.sopt.dto.request.UpdatePostRequest;
import org.sopt.dto.response.PostPageResponse;
import org.sopt.dto.response.PostResponse;
import org.sopt.global.response.ApiResponse;
import org.sopt.global.response.SuccessCode;
import org.sopt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestBody CreatePostRequest request) {
        return toResponse(SuccessCode.POST_CREATED, postService.createPost(request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PostPageResponse>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return toResponse(SuccessCode.POST_LIST_FOUND, postService.getPosts(page, size));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long postId) {
        return toResponse(SuccessCode.POST_FOUND, postService.getPostAndIncreaseViewCount(postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(@PathVariable Long postId,
                                                                @RequestBody UpdatePostRequest request) {
        return toResponse(SuccessCode.POST_UPDATED, postService.updatePost(postId, request));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.status(SuccessCode.POST_DELETED.getStatus())
                .body(ApiResponse.success(SuccessCode.POST_DELETED));
    }

    private <T> ResponseEntity<ApiResponse<T>> toResponse(SuccessCode code, T data) {
        return ResponseEntity.status(code.getStatus())
                .body(ApiResponse.success(code, data));
    }
}