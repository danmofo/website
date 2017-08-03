package com.dmoffat.website;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 	Required parameters:
 * 	- --auth.secret, the secret used to sign JWT tokens
 */

@SpringBootApplication(
	exclude = DataSourceAutoConfiguration.class
)
public class BlogApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}
}
