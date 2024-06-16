package com.jey.blogapp.controller;

import com.jey.blogapp.entity.Post;
import com.jey.blogapp.entity.PostTag;
import com.jey.blogapp.entity.PostTagId;
import com.jey.blogapp.entity.Tag;
import com.jey.blogapp.service.PostService;
import com.jey.blogapp.service.PostTagService;
import com.jey.blogapp.service.TagService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TagController {
    private TagService tagService;
    private PostService postService;
    private PostTagService postTagService;

    @Autowired
    public TagController(TagService tagService, PostService postService, PostTagService postTagService) {
        this.tagService = tagService;
        this.postService = postService;
        this.postTagService = postTagService;
    }

    @GetMapping("/editTag")
    public String editTag(@RequestParam("id") int id, @RequestParam("postId") int postId, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("post", postService.findById(postId));
        return "show-post";
    }

    @GetMapping("/deleteTag")
    public String deleteTag(@RequestParam("id") int id, @RequestParam("postId") int postId, Model model) {
        Tag tag = tagService.findById(id);
        Post post = postService.findById(postId);
        PostTagId postTagId = new PostTagId(postId, id);
        PostTag postTag = postTagService.findById(postTagId);

        model.addAttribute("post", post);

        for(int i=0; i<tag.getPostTags().size(); i++) {
            if(postTag.equals(tag.getPostTags().get(i))) {
                tag.getPostTags().remove(i);
                break;
            }
        }

        for(int j=0; j<post.getPostTags().size(); j++) {
            if(postTag.equals(post.getPostTags().get(j))) {
                post.getPostTags().remove(j);
                break;
            }
        }

        postTagService.deleteById(postTagId);

        if(tag.getPostTags().size() == 0) {
            tagService.deleteById(id);
        }


/*
        while(tag.getPostTags().size()>0) {
            PostTag postTag = tag.getPostTags().remove(0);
            post = postTag.getPost();

            for(int j=0; j<post.getPostTags().size(); j++) {
                if(postTag.equals(post.getPostTags().get(j))) {
                    post.getPostTags().remove(j);
                    break;
                }
            }
            postTagService.deleteById(postTag.getId());
        }
*/

//        tagService.deleteById(id);

        return "show-post";
    }
}
