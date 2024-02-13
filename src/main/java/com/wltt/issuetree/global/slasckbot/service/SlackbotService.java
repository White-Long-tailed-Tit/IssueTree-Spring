package com.wltt.issuetree.global.slasckbot.service;

import com.slack.api.Slack;
import com.slack.api.methods.SlackApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
@Service
public class SlackbotService {

    @Value("{slack.token}")
    private String slackToken;
    @Value("{slack.channel-id")
    private String channelId;


    //연습용
    @Transactional
    public void publishMessage() {
        var client = Slack.getInstance().methods();
        var logger = LoggerFactory.getLogger("issueTree-slack-app");

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
