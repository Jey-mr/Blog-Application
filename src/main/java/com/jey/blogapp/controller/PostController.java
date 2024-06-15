package com.jey.blogapp.controller;

import com.jey.blogapp.entity.*;
import com.jey.blogapp.service.PostService;
import com.jey.blogapp.service.PostTagService;
import com.jey.blogapp.service.TagService;
import com.jey.blogapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class PostController {
    private PostService postService;
    private UserService userService;
    private TagService tagService;
    private PostTagService postTagService;

    @Autowired
    public PostController(PostService postService, UserService userService, TagService tagService, PostTagService postTagService) {
        this.postService = postService;
        this.userService = userService;
        this.tagService = tagService;
        this.postTagService = postTagService;
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
        List<Comment> comments = post.getComments();

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);

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

    @GetMapping("/deletePost")
    public String deletePost(@RequestParam("id") int id) {
        postService.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/updatePost")
    public String updatePost(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        User user = userService.findById(userId);
        Post post = postService.findById(id);

        post.setUser(user);
        post.setPublished(true);
        post.setUpdatedAt(getDate());
        post.setTitle(request.getParameter("title"));
        post.setContent(request.getParameter("content"));
        post.setExcerpt(getExcerpt(post.getContent()));
        post.setPublishedAt(request.getParameter("publishedAt"));
        post.setCreatedAt(request.getParameter("createdAt"));

        postService.save(post);

        return "redirect:/";
    }

    @PostMapping("/processForm")
    public String newPost(HttpServletRequest request, Model model) {
        User user = userService.findById(1);
        Post post = new Post();

        post.setTitle(request.getParameter("title"));
        post.setContent(request.getParameter("content"));
        post.setExcerpt(getExcerpt(post.getContent()));
        post.setPublishedAt(getDate());
        post.setCreatedAt(getDate());
        post.setPublished(true);
        user.add(post);
        postService.save(post);

        String tokens[] = request.getParameter("tags").split(",");
        addTag(tokens, post);

        return "redirect:/";
    }

    private void addTag(String[] tokens, Post post) {
        for(String token : tokens) {
            Tag tag = tagService.findByName(token);

            if(tag == null){
                tag = new Tag(token, getDate());
            }

            PostTag postTag = new PostTag(getDate());
            PostTagId postTagId = new PostTagId(post.getId(), tag.getId());

            System.out.println("Post Id: "+post.getId());

            postTag.setId(postTagId);
            post.add(postTag);
            tag.add(postTag);

            if(tagService.findByName(token) == null){
                tagService.save(tag);
            }

            postTagService.save(postTag);
        }
    }

    private String getExcerpt(String content) {
        String excerpt = content.substring(0, Math.min(300, content.length()));
        int index = excerpt.lastIndexOf('.');
        excerpt = excerpt.substring(0, index+1);
        return excerpt;
    }

    private String getDate() {
        Date date = new Date();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return modifiedDate.toString();
    }
}
