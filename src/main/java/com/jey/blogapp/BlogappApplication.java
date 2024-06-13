package com.jey.blogapp;

import com.jey.blogapp.dao.PostRepository;
import com.jey.blogapp.dao.UserRepository;
import com.jey.blogapp.entity.Post;
import com.jey.blogapp.entity.User;
import com.jey.blogapp.service.PostService;
import com.jey.blogapp.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SpringBootApplication
public class BlogappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogappApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(PostService postService, UserService userService, UserRepository userRepository) {
		return runner -> {
			//createUser(userRepository);
			createPosts(postService, userService);
		};
	}

	private void createPosts(PostService postService, UserService userService) {
		Post post = new Post("title", "WH", "World Hello", "2024-06-13", true, "2024-06-13", null);
		User user = userService.findById(11);

		if(user != null) {
			user.add(post);
			postService.save(post);
			System.out.println("It appears here!");
		}

		System.out.println(user);
	}

	private void createUser(UserRepository userRepository) {
		User user = new User("Jey", "jey@gmail.com", "password");
		Post post2 = new Post("title", "WH", "World Hello", "2024-06-13", true, "2024-06-13", null);
		user.add(post2);
		userRepository.save(user);
	}

}
