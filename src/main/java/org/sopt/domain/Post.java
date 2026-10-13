package org.sopt.domain;

import org.sopt.global.exception.ErrorCode;
import org.sopt.global.exception.BusinessException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Post {
    private Long id;
    private String title;
    private String content;
    private Category category;
    private List<String> tags;
    private final String author;
    private final LocalDateTime createdAt;
    private final AtomicInteger viewCount = new AtomicInteger();

    public Post(String title, String content, Category category, List<String> tags, String author) {
        validate(title, content);
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, String content, Category category, List<String> tags) {
        validate(title, content);
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
    }

    public void increaseViewCount() {
        viewCount.incrementAndGet();
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public Long getId() { return id; }

    private void validate(String title, String content) {
        if (title == null || title.isBlank()) {
            throw new BusinessException(ErrorCode.EMPTY_TITLE);
        }
        if (content == null || content.isBlank()) {
            throw new BusinessException(ErrorCode.EMPTY_CONTENT);
        }
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Category getCategory() { return category; }
    public List<String> getTags() { return tags; }
    public String getAuthor() { return author; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public int getViewCount() { return viewCount.get(); }
}