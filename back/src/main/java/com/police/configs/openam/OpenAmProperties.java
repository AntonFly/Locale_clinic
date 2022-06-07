package com.police.configs.openam;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("openam")
@PropertySource({"classpath:/openam.properties"})
@Data
public class OpenAmProperties {

    private String login;
    private String logout;
    private String home;
    private String attributes;
    private Registration registration;

    @Data
    public static class Registration{

        private String info;
        private String data;



    }

}
