package org.sopt.global.response;

import org.springframework.http.HttpStatus;

public enum SuccessCode {
    POST_CREATED(HttpStatus.CREATED, "게시글이 작성되었습니다."),
    POST_LIST_FOUND(HttpStatus.OK, "게시글 목록 조회에 성공했습니다."),
    POST_FOUND(HttpStatus.OK, "게시글 조회에 성공했습니다."),
    POST_UPDATED(HttpStatus.OK, "게시글이 수정되었습니다."),
    POST_DELETED(HttpStatus.OK, "게시글이 삭제되었습니다.");

    private final HttpStatus status;
    private final String message;

    SuccessCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() { return status; }
    public String getMessage() { return message; }
}