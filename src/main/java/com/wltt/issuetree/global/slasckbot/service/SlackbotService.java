package com.wltt.issuetree.global.slasckbot.service;

import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.wltt.issuetree.global.apipayload.code.status.ErrorStatus;
import com.wltt.issuetree.global.apipayload.exception.GeneralException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SlackbotService {
    @Value(value = "${slack.token}")
    String slackToken;

    public void sendSlackMessage(String message, String channelName) {
        try {
            MethodsClient methods = Slack.getInstance().methods(slackToken);
            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channelName)
                    .text(message)
                    .build();

            methods.chatPostMessage(request);
        } catch (Exception e) {
            throw new GeneralException(ErrorStatus.FAIL_SEND_MESSAGE);
        }
    }
}
