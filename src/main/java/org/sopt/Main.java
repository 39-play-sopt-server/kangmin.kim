package org.sopt;

import org.sopt.controller.PostController;
import org.sopt.repository.PostRepository;
import org.sopt.service.PostService;
import org.sopt.view.PostView;

public class Main {
    public static void main(String[] args) {
        PostRepository postRepository = new PostRepository();
        PostService postService = new PostService(postRepository);
        PostController postController = new PostController(postService, new PostView());
        postController.run();
    }
}