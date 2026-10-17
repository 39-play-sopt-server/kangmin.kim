package org.sopt.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    EMPTY_TITLE(HttpStatus.BAD_REQUEST, "제목을 입력해주세요."),
    EMPTY_CONTENT(HttpStatus.BAD_REQUEST, "본문을 입력해주세요."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    NOT_FOUND_PATH(HttpStatus.NOT_FOUND, "존재하지 않는 경로입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "지원하지 않는 HTTP 메서드입니다."),
    INVALID_PAGE(HttpStatus.BAD_REQUEST, "page는 0 이상, size는 1~100 사이여야 합니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() { return status; }
    public String getMessage() { return message; }
}