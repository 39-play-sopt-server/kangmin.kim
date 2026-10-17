package org.sopt.domain;

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
}