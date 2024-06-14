package com.jey.blogapp;

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
	public CommandLineRunner commandLineRunner(TagService tagService, PostService postService, UserService userService) {
		return runner -> {
			testing(tagService, postService);
		};

	}

	private void testing(TagService tagService, PostService postService) {
		/*
		* CREATING TAG AND LINKING IT TO POST(S)
		*/

//		Tag tag = new Tag("Computer Science", "2024-06-14");
//		Post post = postService.findById(1);
//		tag.add(post);
//		tagService.save(tag);

//		------X------


		/*
		* DELETING TAG
		*/

//		tagService.deleteById(10);

//		------X------


		/*
		* ADDING EXISTING TAG TO POST(S):
		*/

//		Tag tag = tagService.findById(8);
//		Post post1 = postService.findById(2);
//		Post post2 = postService.findById(3);

//		tag.add(post1);
//		tag.add(post2);
//		tagService.save(tag);

//		System.out.println("Post1: ");
//		System.out.println(post1.getTags().size());

//		System.out.println("Post2");
//		System.out.println(post2.getTags().size());

//		------X------


	}
}
