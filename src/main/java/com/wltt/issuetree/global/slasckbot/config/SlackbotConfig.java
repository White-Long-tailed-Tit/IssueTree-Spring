package com.wltt.issuetree.global.slasckbot.config;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application-SLACK.properties")
public class SlackbotConfig {

    @Value("${slack.token}")
    private String token;

    @Bean
    public MethodsClient methodsClient() {
        return Slack.getInstance().methods(token);

    }
}
