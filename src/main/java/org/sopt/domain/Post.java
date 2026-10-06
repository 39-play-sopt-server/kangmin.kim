package org.sopt.domain;

import org.sopt.exception.InvalidInputException;

import java.time.LocalDateTime;
import java.util.List;

public class Post {
    private String title;
    private String content;
    private Category category;
    private List<String> tags;
    private final String author;
    private final LocalDateTime createdAt;
    private int viewCount;

    public Post(String title, String content, Category category, List<String> tags, String author) {
        validate(title, content);
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
        this.author = author;
        this.createdAt = LocalDateTime.now();
        this.viewCount = 0;
    }

    public void update(String title, String content, Category category, List<String> tags) {
        validate(title, content);
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = tags;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    private void validate(String title, String content) {
        if (title.isBlank()) {
            throw new InvalidInputException("제목을 입력해주세요.");
        }
        if (content.isBlank()) {
            throw new InvalidInputException("본문을 입력해주세요.");
        }
    }

    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Category getCategory() { return category; }
    public List<String> getTags() { return tags; }
    public String getAuthor() { return author; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public int getViewCount() { return viewCount; }
}