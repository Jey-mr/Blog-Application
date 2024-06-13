package com.jey.blogapp.controller;

import com.jey.blogapp.entity.Post;
import com.jey.blogapp.entity.User;
import com.jey.blogapp.service.PostService;
import com.jey.blogapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PostController {
    private PostService postService;
    private UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String listPosts(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "list-posts";
    }

    @GetMapping("/post{postId}")
    public String showPost(Model model, @PathVariable int postId) {
        Post post = postService.findById(postId);
        model.addAttribute("post", post);
        return "show-post";
    }

    @GetMapping("/showForm")
    public String showForm(Model model) {
        return "new-post";
    }

    @GetMapping("/editPost")
    public String editPost(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Post post = postService.findById(id);
        model.addAttribute("post", post);
        return "edit-post";
    }

    @PostMapping("/updatePost")
    public String updatePost(HttpServletRequest request, @ModelAttribute Post post) {
        int id = Integer.parseInt(request.getParameter("userId"));
        User user = userService.findById(id);

        post.setUser(user);
        post.setPublished(true);
        post.setCreatedAt(null);
        post.setUpdatedAt(null);

        postService.save(post);
        return "redirect:/";
    }

    @PostMapping("/processForm")
    public String newPost(HttpServletRequest request, Model model) {
        User user = userService.findById(1);
        Post post = new Post();
        post.setTitle(request.getParameter("title"));
        post.setContent(request.getParameter("content"));
        post.setPublishedAt("2024-06-13");
        post.setPublished(true);
        post.setExcerpt("temp");
        user.add(post);
        postService.save(post);

        return listPosts(model);
    }
}
