package org.sopt;

import java.util.ArrayList;
import java.util.List;

public class PostController {
    private final List<Post> posts = new ArrayList<>();
    private final PostView view = new PostView();

    public void run() {
        while (true) {
            int command = view.inputMenu();
            switch (command) {
                case 1 -> create();
                case 2 -> readAll();
                case 3 -> read();
                case 4 -> update();
                case 5 -> delete();
                case 6 -> {
                    view.printMessage("프로그램을 종료합니다.");
                    return;
                }
                default -> view.printMessage("잘못된 입력입니다.");
            }
        }
    }

    private void create() {
        String title = view.input("제목: ");
        String content = view.input("내용: ");
        posts.add(new Post(title, content));
        view.printMessage("게시글이 작성되었습니다.");
    }

    private void readAll() {
        if (isEmpty()) return;
        view.printPosts(posts);
    }

    private void read() {
        if (isEmpty()) return;
        int index = view.inputIndex("조회할 게시글 번호: ");
        if (!isValid(index)) return;
        view.printPost(posts.get(index));
    }

    private void update() {
        if (isEmpty()) return;
        int index = view.inputIndex("수정할 게시글 번호: ");
        if (!isValid(index)) return;
        String newTitle = view.input("새로운 제목: ");
        String newContent = view.input("새로운 내용: ");
        posts.get(index).update(newTitle, newContent);
        view.printMessage("게시글이 수정되었습니다.");
    }

    private void delete() {
        if (isEmpty()) return;
        int index = view.inputIndex("삭제할 게시글 번호: ");
        if (!isValid(index)) return;
        posts.remove(index);
        view.printMessage("게시글이 삭제되었습니다.");
    }

    private boolean isEmpty() {
        if (posts.isEmpty()) {
            view.printMessage("게시글이 없습니다.");
            return true;
        }
        return false;
    }

    private boolean isValid(int index) {
        if (index < 0 || index >= posts.size()) {
            view.printMessage("존재하지 않는 게시글입니다.");
            return false;
        }
        return true;
    }
}