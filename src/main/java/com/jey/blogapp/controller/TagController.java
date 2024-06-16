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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        delete(id, postId, model);
        return "redirect:/post" + postId;
    }

    private void delete(int id, int postId, Model model) {
        Tag tag = tagService.findById(id);
        Post post = postService.findById(postId);
        PostTagId postTagId = new PostTagId(postId, id);
        PostTag postTag = postTagService.findById(postTagId);

        if(model != null)
            model.addAttribute("post", post);

        removePostTagFromTag(tag, postTag);
        removePostTagFromPost(post, postTag);
        postTagService.deleteById(postTagId);

        if(tag.getPostTags().size() == 0) {
            tagService.deleteById(id);
        }
    }

    private void removePostTagFromTag(Tag tag, PostTag postTag) {
        for(int i=0; i<tag.getPostTags().size(); i++) {
            if(postTag.equals(tag.getPostTags().get(i))) {
                tag.getPostTags().remove(i);
                break;
            }
        }
    }

    private void removePostTagFromPost(Post post, PostTag postTag) {
        for(int j=0; j<post.getPostTags().size(); j++) {
            if(postTag.equals(post.getPostTags().get(j))) {
                post.getPostTags().remove(j);
                break;
            }
        }
    }

    @PostMapping("/updateTag")
    public String updateTag(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        int postId = Integer.parseInt(request.getParameter("postId"));
        Tag tag = tagService.findById(id);
        Post post = postService.findById(postId);
        String token = request.getParameter("tag");
        model.addAttribute("post", post);

        if(tag.getName() != token) {
            if(tag.getPostTags().size() == 1){
                update(token, tag, post);
            } else {
                delete(id, postId, model);
                addTag(token, post);
            }
        }

        return "redirect:/post" + postId;
    }

    private void update(String token, Tag tag, Post post) {
        Tag tempTag = tagService.findByName(token);
        PostTag postTag = tag.getPostTags().get(0);


        if(tempTag == null){
            tag.setName(token);
            tag.setUpdatedAt(getDate());
            tagService.save(tag);
        } else {
//            PostTagId postTagId = postTag.getId();
//            postTagId.setTagId(tempTag.getId());
//            tempTag.add(postTag);

            postTag = new PostTag(postTag.getCreatedAt());
            PostTagId postTagId = new PostTagId(post.getId(), tempTag.getId());

            postTag.setId(postTagId);
            post.add(postTag);
            tempTag.add(postTag);

            delete(tag.getId(), post.getId(), null);
        }

        postTag.setUpdatedAt(getDate());
        postTagService.save(postTag);
    }

    private void addTag(String token, Post post) {
        Tag tag = tagService.findByName(token);

//            System.out.println(tag);

        if(tag == null){
            tag = new Tag(token, getDate());
//                System.out.println("It should not come here!!");
        }

        PostTag postTag = new PostTag(getDate());
        PostTagId postTagId = new PostTagId(post.getId(), tag.getId());

//            System.out.println("Post Id: "+post.getId());

        postTag.setId(postTagId);
        post.add(postTag);
        tag.add(postTag);

        if(tagService.findByName(token) == null){
            tagService.save(tag);
        }

        postTagService.save(postTag);
    }

    private String getDate() {
        Date date = new Date();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return modifiedDate.toString();
    }
}
