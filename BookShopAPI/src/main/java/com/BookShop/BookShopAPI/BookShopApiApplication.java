package com.BookShop.BookShopAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableMongoAuditing
public class BookShopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookShopApiApplication.class, args);
	}

}


/*
	The @EnableMongoAuditing annotation is used in Spring Data MongoDB to enable auditing features, which allow
	you to automatically populate auditing fields such as creation and modification dates, and the users who created
	or modified the documents.
 */