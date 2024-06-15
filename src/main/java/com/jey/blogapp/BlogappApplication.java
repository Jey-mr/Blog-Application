package com.jey.blogapp;

import com.jey.blogapp.dao.PostTagRepository;
import com.jey.blogapp.entity.Post;
import com.jey.blogapp.entity.PostTag;
import com.jey.blogapp.entity.PostTagId;
import com.jey.blogapp.entity.Tag;
import com.jey.blogapp.service.PostService;
import com.jey.blogapp.service.PostTagService;
import com.jey.blogapp.service.TagService;
import com.jey.blogapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BlogappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogappApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PostTagService postTagService, TagService tagService, PostService postService) {
		return runner -> {
			testing(tagService, postService, postTagService);
		};

	}

	private void testing(TagService tagService, PostService postService, PostTagService postTagService) {
		/*
		* CREATING TAG AND LINKING IT TO POST(S)
		*/

//		addAndLinkTag(tagService, postService, postTagService);

//		------X------



		/*
		* DELETING TAG
		*/

//		deleteTag(60, tagService, postTagService);

//		------X------


		/*
		* UPDATE EXISTING TAG(S):
		*/

//		updateTag(tagService);

//		------X------




	}

	private void updateTag(TagService tagService) {
		Tag tag = tagService.findById(1);

		tag.setName("Tag Updated again");
		tagService.save(tag);

	}


	private void addAndLinkTag(TagService tagService, PostService postService, PostTagService postTagService) {
		Tag tag = new Tag("tag", "2024-06-15");

		for(int i=1; i<=7; i++) {
			Post post = postService.findById(i);
			PostTag postTag = new PostTag("2024-06-15");
			PostTagId postTagId = new PostTagId(post.getId(), tag.getId());

			postTag.setId(postTagId);
			post.add(postTag);
			tag.add(postTag);

			if(tagService.findById(tag.getId()) == null)
				tagService.save(tag);

			postTagService.save(postTag);
		}

	}

	private void deleteTag(int id, TagService tagService, PostTagService postTagService) {
		Tag tag = tagService.findById(id);

		System.out.println("Before: "+tag.getPostTags().size());


		while(tag.getPostTags().size()>0) {
			PostTag postTag = tag.getPostTags().remove(0);
			Post post = postTag.getPost();

			for(int j=0; j<post.getPostTags().size(); j++) {
				if(postTag.equals(post.getPostTags().get(j))) {
					post.getPostTags().remove(j);
					break;
				}
			}

			postTagService.deleteById(postTag.getId());
		}

		tagService.deleteById(id);

		System.out.println("After: "+tag.getPostTags().size());
	}
}
