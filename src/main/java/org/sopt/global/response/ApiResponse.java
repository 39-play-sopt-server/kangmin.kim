package org.sopt.global.response;

import org.sopt.global.exception.ErrorCode;

public record ApiResponse<T>(int status, String message, T data) {

    public static <T> ApiResponse<T> success(SuccessCode code, T data) {
        return new ApiResponse<>(code.getStatus().value(), code.getMessage(), data);
    }

    public static ApiResponse<Void> success(SuccessCode code) {
        return new ApiResponse<>(code.getStatus().value(), code.getMessage(), null);
    }

    public static ApiResponse<Void> fail(ErrorCode code) {
        return new ApiResponse<>(code.getStatus().value(), code.getMessage(), null);
    }
}