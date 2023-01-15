package com.clinic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class CourseProjectApplication extends SpringBootServletInitializer {

	//For deploy purposes
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder)
	{ return builder.sources(CourseProjectApplication.class); }

	public static void main(String[] args)
	{ SpringApplication.run(CourseProjectApplication.class, args); }

}

