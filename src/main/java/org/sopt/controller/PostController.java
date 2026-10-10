package org.sopt.controller;

import org.sopt.domain.Category;
import org.sopt.domain.Post;
import org.sopt.exception.InvalidInputException;
import org.sopt.exception.PostNotFoundException;
import org.sopt.service.PostService;
import org.sopt.view.PostView;

import java.util.List;

public class PostController {
    private final PostService postService;
    private final PostView postView;

    public PostController(PostService postService, PostView postView) {
        this.postService = postService;
        this.postView = postView;
    }

    public void run() {
        while (true) {
            try {
                switch (postView.inputMenu()) {
                    case 1 -> create();
                    case 2 -> readAll();
                    case 3 -> read();
                    case 4 -> update();
                    case 5 -> delete();
                    case 6 -> {
                        postView.printMessage("프로그램을 종료합니다.");
                        return;
                    }
                    default -> throw new InvalidInputException("잘못된 입력입니다.");
                }
            } catch (InvalidInputException | PostNotFoundException e) {
                postView.printMessage(e.getMessage());
            }
        }
    }

    private void create() {
        String title = postView.input("제목: ");
        String content = postView.input("본문: ");
        Category category = Category.fromNumber(postView.inputCategory());
        List<String> tags = postView.inputTags();
        String author = postView.input("작성자: ");
        postService.createPost(title, content, category, tags, author);
        postView.printMessage("게시글이 작성되었습니다.");
    }

    private void readAll() {
        List<Post> posts = postService.getAllPosts();
        if (posts.isEmpty()) {
            postView.printMessage("게시글이 없습니다.");
            return;
        }
        postView.printPosts(posts);
    }

    private void read() {
        int index = postView.inputNumber("조회할 번호: ") - 1;
        postView.printPost(postService.getPost(index));
    }

    private void update() {
        int index = postView.inputNumber("수정할 번호: ") - 1;
        String title = postView.input("새 제목: ");
        String content = postView.input("새 본문: ");
        Category category = Category.fromNumber(postView.inputCategory());
        List<String> tags = postView.inputTags();
        postService.updatePost(index, title, content, category, tags);
        postView.printMessage("게시글이 수정되었습니다.");
    }

    private void delete() {
        int index = postView.inputNumber("삭제할 번호: ") - 1;
        postService.deletePost(index);
        postView.printMessage("게시글이 삭제되었습니다.");
    }
}