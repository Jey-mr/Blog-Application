package com.jey.blogapp.controller;

import com.jey.blogapp.entity.Comment;
import com.jey.blogapp.entity.Post;
import com.jey.blogapp.service.CommentService;
import com.jey.blogapp.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class CommentController {
    private PostService postService;
    private CommentService commentService;

    @Autowired
    public CommentController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public String addComment(HttpServletRequest request, Model model) {
        int postId = Integer.parseInt(request.getParameter("postId"));
        Post post = postService.findById(postId);
        Comment comment = new Comment();

        comment.setName(request.getParameter("name"));
        comment.setEmail(request.getParameter("email"));
        comment.setComment(request.getParameter("comment"));
        comment.setCreatedAt(getDate());
        post.add(comment);
        commentService.save(comment);

        return "redirect:/post" + postId;
    }

    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("id") int id, @RequestParam("postId") int postId) {
        commentService.deleteById(id);
        return "redirect:/post" + postId;
    }

    @GetMapping("/editComment")
    public String editComment(@RequestParam("id") int id, Model model) {
        Comment comment = commentService.findById(id);
        Post post = comment.getPost();
        List<Comment> comments = post.getComments();

        model.addAttribute("id", id);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "show-post";
    }

    @PostMapping("/updateComment")
    public String updateComment(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        int postId = Integer.parseInt(request.getParameter("postId"));
        Post post = postService.findById(postId);
        Comment comment = commentService.findById(id);

        comment.setComment(request.getParameter("comment"));
        comment.setPost(post);
        comment.setUpdatedAt(getDate());
        commentService.save(comment);

        System.out.println(comment);

        return "redirect:/post" + postId;
    }

    private String getDate() {
        Date date = new Date();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return modifiedDate.toString();
    }
}
