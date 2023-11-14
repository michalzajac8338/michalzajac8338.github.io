package com.michal.booksylikeapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BooksyLikeAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BooksyLikeAppApplication.class, args);
	}

}
