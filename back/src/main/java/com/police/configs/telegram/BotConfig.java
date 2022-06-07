package com.police.configs.telegram;

import com.police.configs.telegram.BotProperties;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;

@Configuration
public class BotConfig {

    @Autowired
    BotProperties properties;

    @Bean
    DefaultBotOptions defaultBotOptions(){
        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyHost(properties.getProxy().getHost());
        botOptions.setProxyPort(properties.getProxy().getPort());
        botOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);
        return botOptions;
    }

}
