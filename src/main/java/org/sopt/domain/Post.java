package org.sopt.domain;

import jakarta.persistence.*;
import org.sopt.global.exception.BusinessException;
import org.sopt.global.exception.ErrorCode;

import java.util.List;

@Entity
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    private String tags;
    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    protected Post() {}

    public Post(String title, String content, Category category, List<String> tags, Member member) {
        validate(title, content, category);
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = joinTags(tags);
        this.member = member;
    }

    public void update(String title, String content, Category category, List<String> tags) {
        validate(title, content, category);
        this.title = title;
        this.content = content;
        this.category = category;
        this.tags = joinTags(tags);
    }

    public void increaseViewCount() {
        viewCount++;
    }

    private void validate(String title, String content, Category category) {
        if (title == null || title.isBlank()) {
            throw new BusinessException(ErrorCode.EMPTY_TITLE);
        }
        if (content == null || content.isBlank()) {
            throw new BusinessException(ErrorCode.EMPTY_CONTENT);
        }
        if (category == null) {
            throw new BusinessException(ErrorCode.EMPTY_CATEGORY);
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
    public Member getMember() { return member; }
    public int getViewCount() { return viewCount; }
}