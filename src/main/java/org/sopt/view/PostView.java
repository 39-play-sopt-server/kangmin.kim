package org.sopt.view;

import org.sopt.domain.Category;
import org.sopt.domain.Post;
import org.sopt.exception.InvalidInputException;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PostView {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final Scanner scanner = new Scanner(System.in);

    public int inputMenu() {
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 작성  2. 목록  3. 조회  4. 수정  5. 삭제  6. 종료");
        return inputNumber("선택: ");
    }

    public String input(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public int inputNumber(String message) {
        try {
            return Integer.parseInt(input(message).trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException("숫자를 입력해주세요.");
        }
    }

    public int inputCategory() {
        Category[] categories = Category.values();
        for (int i = 0; i < categories.length; i++) {
            System.out.print((i + 1) + "." + categories[i].getDisplayName() + " ");
        }
        return inputNumber("\n카테고리: ");
    }

    public List<String> inputTags() {
        String value = input("태그(쉼표로 구분): ");
        if (value.isBlank()) {
            return List.of();
        }
        return Arrays.stream(value.split(",")).map(String::trim).toList();
    }

    public void printPosts(List<Post> posts) {
        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);
            System.out.println((i + 1) + ". [" + post.getCategory().getDisplayName() + "] "
                    + post.getTitle() + " - " + post.getAuthor());
        }
    }

    public void printPost(Post post) {
        System.out.println("제목: " + post.getTitle());
        System.out.println("카테고리: " + post.getCategory().getDisplayName());
        System.out.println("작성자: " + post.getAuthor());
        System.out.println("작성일: " + post.getCreatedAt().format(FORMATTER));
        System.out.println("조회수: " + post.getViewCount());
        System.out.println("태그: " + post.getTags());
        System.out.println("본문: " + post.getContent());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}