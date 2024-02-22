package com.wltt.issuetree.global.slasckbot.service;


import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.composition.TextObject;
import com.wltt.issuetree.global.apipayload.code.status.ErrorStatus;
import com.wltt.issuetree.global.apipayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

@Service
@RequiredArgsConstructor
public class SlackbotService {
    private final MethodsClient methodsClient;

    @Value("${slack.token}")
    private String slackToken;

    public void chatMessage(
            final String text,
            final String headerContent,
            final String channelNameOrIdOrUserId
    ) {
        ChatPostMessageRequest messageRequest = ChatPostMessageRequest.builder()
                .channel(channelNameOrIdOrUserId)
                .blocks(asBlocks(
                                header(
                                        header -> header.text(plainText(headerContent))
                                ),
                                divider(),
                                section(s -> s.text(markdownText(text)))
                        )
                )
                .build();
        try {
            methodsClient.chatPostMessage(messageRequest);
        } catch (SlackApiException | IOException e) {
            throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }

    public void chatMessage(
            final List<TextObject> textObjectList,
            final String headerContent,
            final String channelNameOrIdOrUserId
    ) {
        ChatPostMessageRequest messageRequest = ChatPostMessageRequest.builder()
                .channel(channelNameOrIdOrUserId)
                .blocks(asBlocks(
                                header(
                                        header -> header.text(plainText(headerContent))
                                ),
                                divider(),
                                section(section -> section.fields(textObjectList))
                        )
                )
                .build();
        try {
            methodsClient.chatPostMessage(messageRequest);
        } catch (SlackApiException | IOException e) {
            throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }
}
