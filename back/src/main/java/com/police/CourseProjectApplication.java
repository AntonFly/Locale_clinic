package com.police;

import com.police.bot.NYPDTbot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class CourseProjectApplication{

	//For deploy purposes
/*	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(CourseProjectApplication.class);
	}*/

	public static void main(String[] args) {
        ApiContextInitializer.init();
		SpringApplication.run(CourseProjectApplication.class, args);
	}

}

