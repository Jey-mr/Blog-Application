package com.jey.blogapp;

import com.jey.blogapp.dao.PostTagRepository;
import com.jey.blogapp.entity.Post;
import com.jey.blogapp.entity.PostTag;
import com.jey.blogapp.entity.PostTagId;
import com.jey.blogapp.entity.Tag;
import com.jey.blogapp.service.PostService;
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
	public CommandLineRunner commandLineRunner(TagService tagService, PostService postService, PostTagRepository PTRepository, UserService userService) {
		return runner -> {
			testing(tagService, postService, PTRepository);
		};

	}

	private void testing(TagService tagService, PostService postService, PostTagRepository PTRepository) {
		/*
		* CREATING TAG AND LINKING IT TO POST(S)
		*/

//		Tag tag = new Tag("Computer Science", "2024-06-15");
//		Post post = postService.findById(2);
//		PostTag postTag = new PostTag("2024-06-15");
//		PostTagId postTagId = new PostTagId(post.getId(), tag.getId());
//
//		postTag.setId(postTagId);
//		tag.add(postTag);
//		post.add(postTag);
//		tagService.save(tag);
//		PTRepository.save(postTag);

//		------X------



		/*
		* DELETING TAG
		*/
//		addAndLinkTag(tagService, postService, PTRepository);
		deleteTag(56, tagService, PTRepository);

//		------X------


		/*
		* ADDING EXISTING TAG TO POST(S):
		*/

//		Tag tag = tagService.findById(30);
//		Post post1 = postService.findById(3);
//		Post post2 = postService.findById(5);
//		PostTag postTag1 = new PostTag("2024-06-15");
//		PostTag postTag2 = new PostTag("2024-06-15");
//		PostTagId id1 = new PostTagId(post1.getId(), tag.getId());
//		PostTagId id2 = new PostTagId(post2.getId(), tag.getId());
//
//		postTag1.setId(id1);
//		postTag2.setId(id2);
//		tag.add(postTag1);
//		tag.add(postTag2);
//		post1.add(postTag1);
//		post2.add(postTag2);
//		PTRepository.save(postTag1);
//		PTRepository.save(postTag2);

//		------X------

	}

	private void addAndLinkTag(TagService tagService, PostService postService, PostTagRepository ptRepository) {
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

			ptRepository.save(postTag);
		}

	}

	private void deleteTag(int id, TagService tagService, PostTagRepository PTRepository) {
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

			PTRepository.delete(postTag);
		}

		tagService.deleteById(id);

		System.out.println("After: "+tag.getPostTags().size());
	}
}
