package org.sopt.domain;

import org.sopt.exception.InvalidInputException;

public enum Category {
    DAILY("일상"),
    REVIEW("리뷰"),
    RESTAURANT("맛집"),
    TRAVEL("여행"),
    INFO("정보");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Category fromNumber(int number) {
        Category[] categories = values();
        if (number < 1 || number > categories.length) {
            throw new InvalidInputException("존재하지 않는 카테고리입니다.");
        }
        return categories[number - 1];
    }
}