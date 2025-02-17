package com.jey.blogapp.controller;

import com.jey.blogapp.entity.*;
import com.jey.blogapp.service.PostService;
import com.jey.blogapp.service.PostTagService;
import com.jey.blogapp.service.TagService;
import com.jey.blogapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class PostController {
    private PostService postService;
    private UserService userService;
    private TagService tagService;
    private PostTagService postTagService;
    private Authentication authentication;

    @Autowired
    public PostController(PostService postService, UserService userService, TagService tagService, PostTagService postTagService) {
        this.postService = postService;
        this.userService = userService;
        this.tagService = tagService;
        this.postTagService = postTagService;
    }

    @GetMapping("/")
    public String listPosts(HttpServletRequest request, Model model) {
        String keyword = request.getParameter("keyword");
        String sortBy = request.getParameter("sortBy");
        String order = request.getParameter("order");
        List<Post> posts = new ArrayList<>();
        Page<Post> page = null;
        int pageNo = 0;
        int pageSize = 4;

        if(request.getParameter("pageNo") != null) {
            pageNo = Integer.parseInt(request.getParameter("pageNo"));

            if(request.getParameter("navigate") != null) {
                pageNo++;
            } else {
                pageNo--;
            }
        }

        pageNo = Math.max(pageNo, 0);


        if(order == null)
            order = "asc";

        if(keyword != null) {
            keyword = keyword.trim();
            keyword = (keyword.isEmpty()) ? null : keyword;
        }

        if(sortBy != null) {
            sortBy = sortBy.trim();
            sortBy = (sortBy.isEmpty()) ? null : sortBy;
        }


        if(keyword == null) {
           if(sortBy == null) {
               int n = 1;
               List<Post> tempPosts;

               for(int i=0; true; i++) {
                   page = postService.findAll(i, pageSize);
                   tempPosts = page.getContent();

                   if(tempPosts.size() == 0)
                       break;

                   for(Post post: tempPosts)
                       posts.add(post);
               }
//               page = postService.findAll(pageNo, pageSize);
//               posts = page.getContent();
           } else {
               List<Post> tempPosts;

               for(int i=0; true; i++) {
                   page = postService.findPostsSortBy(sortBy, order, i, pageSize);
                   tempPosts = page.getContent();

                   if(tempPosts.size() == 0)
                       break;

                   for(Post post: tempPosts)
                       posts.add(post);
               }
//               page = postService.findPostsSortBy(sortBy, order, pageNo, pageSize);
//               posts = page.getContent();
           }
        } else {
            if(sortBy == null) {
                List<Post> tempPosts;

                for(int i=0; true; i++) {
                    tempPosts = postService.findPostsWithKeyword(keyword, i, pageSize);

                    if(tempPosts.size() == 0)
                        break;

                    for(Post post: tempPosts)
                        posts.add(post);
                }
//                posts = postService.findPostsWithKeyword(keyword, pageNo, pageSize);
            } else {
                List<Post> tempPosts;

                for(int i=0; true; i++) {
                    tempPosts = postService.findPostsSortByWithKeyword(sortBy, order, keyword, pageNo, pageSize);

                    if(tempPosts.size() == 0)
                        break;

                    for(Post post: tempPosts)
                        posts.add(post);
                }
//                posts = postService.findPostsSortByWithKeyword(sortBy, order, keyword, pageNo, pageSize);
            }
        }

        List<Tag> tags = tagService.findAll();
        model.addAttribute("tags", tags);
        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        posts = filterPosts(request, posts);
        List<Post> postsSend = new ArrayList<>();

        for(int i=0; i<pageSize; i++) {
            int index = pageNo * pageSize + i;

            if(index >= posts.size())
                break;

            postsSend.add(posts.get(index));
        }

        model.addAttribute("posts", postsSend);
        model.addAttribute("keyword", keyword);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("order", order);
        model.addAttribute("pageNo", pageNo);

        return "list-posts";
    }


    private List<Post> filterPosts(HttpServletRequest request, List<Post> posts) {
        boolean userCheck = false;
        boolean publishedCheck = false;
        boolean tagCheck = false;

//        List<Post> posts = new ArrayList<>();
        List<User> users = userService.findAll();
        List<Tag> tags = tagService.findAll();

//        for(Post post: postsUnmodified) {
//            posts.add(post);
//        }

        for(User user: users) {
            if(request.getParameter(user.getName()) != null) {
                userCheck = true;
                break;
            }
        }

        for(Tag tag: tags) {
            if(request.getParameter(tag.getName()) != null) {
                tagCheck = true;
                break;
            }
        }

        for(Post post: posts) {
            if(request.getParameter(post.getPublishedAt()) != null) {
                publishedCheck = true;
                break;
            }
        }

        for(int i=0; i<posts.size(); i++) {
            Post post = posts.get(i);

            if(userCheck  &&  request.getParameter(post.getUser().getName()) == null) {
                posts.remove(i);
                i--;
                continue;
            };

            if(publishedCheck  &&  request.getParameter(post.getPublishedAt()) == null) {
                posts.remove(i);
                i--;
                continue;
            }

            List<PostTag> postTags = post.getPostTags();

            int j;
            for(j = 0; j < postTags.size(); j++) {
                PostTag postTag = postTags.get(j);
                String tag = postTag.getTag().getName();

                if (request.getParameter(tag) != null) {
                    break;
                }
            }

            if (tagCheck  &&  j == postTags.size()){
                posts.remove(i);
                i--;
            }
        }

        return posts;
    }

    @GetMapping("/post{postId}")
    public String showPost(Model model, @PathVariable int postId) {
        Post post = postService.findById(postId);
        List<Comment> comments = post.getComments();
        boolean allowed = false;

        authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String userName = authentication.getName();
            List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();

            if(userName.equals(post.getUser().getName())) {
                allowed = true;
            }

            for(GrantedAuthority authority: authorities) {
                if(authority.getAuthority().equals("ROLE_ADMIN")) {
                    allowed = true;
                    break;
                }
            }

        }

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("allowed", allowed);

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
        Post post = postService.findById(id);

        while(post.getPostTags().size() > 0) {
            PostTag postTag = post.getPostTags().get(0);
            Tag tag = postTag.getTag();
            delete(tag.getId(), id, null);
        }

        postService.deleteById(id);
        return "redirect:/";
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

        String tokens[] = request.getParameter("tags").split(", ");
        addTag(tokens, post);

        return "redirect:/post" + id;
    }

    @PostMapping("/processForm")
    public String newPost(HttpServletRequest request, Model model) {
        authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByName(authentication.getName());
        Post post = new Post();

        post.setTitle(request.getParameter("title"));
        post.setContent(request.getParameter("content"));
        post.setExcerpt(getExcerpt(post.getContent()));
        post.setPublishedAt(getDate());
        post.setCreatedAt(getDate());
        post.setPublished(true);
        user.add(post);
        postService.save(post);

        String tokens[] = request.getParameter("tags").split(", ");
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
        excerpt = excerpt.substring(0, index + 1);
        return excerpt;
    }

    private String getDate() {
        Date date = new Date();
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        return modifiedDate.toString();
    }
}
