package com.wltt.issuetree.global.slasckbot.service;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class SlackbotServiceTest {
    @Value("${slack.token}")
    private String slackToken;

    @Value("${slack.channel-id")
    private String channelId;

    public void publishMessage() {
        var client = Slack.getInstance().methods();
        var logger = LoggerFactory.getLogger("issueTree-slack-app");
        System.out.println(slackToken);
        System.out.println(channelId);

        try {
            var result = client.chatPostMessage(r -> r
                    .token(slackToken)
                    .channel(channelId)
                    .text("테스트 중")
            );
            logger.info("result {}", result);
        } catch (IOException | SlackApiException e) {
            logger.error("error: {}", e.getMessage(), e);
        }
    }


}
