package com.wltt.issuetree.global.slasckbot.config;

import com.slack.api.app_backend.slash_commands.payload.SlashCommandPayload;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath : application-SLACK.properties")
public class SlackbotConfig {
    private final Logger log = LoggerFactory.getLogger(SlackbotConfig.class);

    private final String token;
    private final String signingSecret;

    public SlackbotConfig(Environment env){
        this.token = env.getProperty("${slack.token}");
        this.signingSecret = env.getProperty("${slack.signing-secret}");
    }

    // command 입력 시 모두에게 알림
    @Qualifier("IssueTree")
    @Bean
    public App issueTreeApp(){
        AppConfig appConfig = AppConfig.builder().singleTeamBotToken(token).signingSecret(signingSecret).build();
        App issueTreeApp = new App(appConfig);

        issueTreeApp.command("/해결", (req, ctx) -> {
            SlashCommandPayload pl = req.getPayload();
            log.info("command : " + pl.getCommand());

            MethodsClient client = ctx.client();
            ChatPostMessageResponse msg = client.chatPostMessage(r -> r
                    .channel(pl.getChannelId())
                        .text("이슈가 해결되었습니다! 관련 정보가 이슈트리에 저장되었습니다."));
            if (!msg.isOk()){
                ctx.logger.error("chat.postMessage faild: {}", msg.getError());
            }
            return ctx.ack();
        });
        return issueTreeApp;
    }
}
