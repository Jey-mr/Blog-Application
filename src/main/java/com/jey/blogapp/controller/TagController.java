package com.jey.blogapp.controller;

import com.jey.blogapp.service.PostService;
import com.jey.blogapp.service.TagService;
import org.springframework.stereotype.Controller;

@Controller
public class TagController {
    private TagService tagService;
    private PostService postService;
}
