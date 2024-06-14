package com.jey.blogapp;

import com.jey.blogapp.entity.Comment;
import com.jey.blogapp.entity.Post;
import com.jey.blogapp.service.CommentService;
import com.jey.blogapp.service.PostService;
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
}
