package com.police.configs.telegram;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("bot")
@PropertySource({"classpath:/bot.properties"})
@Data
public class BotProperties {

    private String token;
    private String name;
    private Proxy proxy;

    @Data
    public static class Proxy {

        private String host;
        private int port;

    }
}
