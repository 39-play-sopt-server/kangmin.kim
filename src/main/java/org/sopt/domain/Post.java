package org.sopt.domain;

import jakarta.persistence.*;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String tags;
    private String author;
    private LocalDateTime createdAt;
    private int viewCount;

    protected Post() {}

    public Post(String title, String content, Category category, List<String> tags, String author) {
        validate(title, content);
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = joinTags(tags);
        this.author = author;
        this.createdAt = LocalDateTime.now();
    }

    public void update(String title, String content, Category category, List<String> tags) {
        validate(title, content);
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = joinTags(tags);
    }

    public void increaseViewCount() {
        viewCount++;
    }

    private void validate(String title, String content) {
        if (title == null || title.isBlank()) {
            throw new BusinessException(ErrorCode.EMPTY_TITLE);
        }
        if (content == null || content.isBlank()) {
            throw new BusinessException(ErrorCode.EMPTY_CONTENT);
        }
    }

    private static String joinTags(List<String> tags) {
        return tags == null ? null : String.join(",", tags);
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public Category getCategory() { return category; }
    public List<String> getTags() {
        if (tags == null) return null;
        if (tags.isEmpty()) return List.of();
        return List.of(tags.split(","));
    }
    public String getAuthor() { return author; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public int getViewCount() { return viewCount; }
}