package com.wltt.issuetree.global.slasckbot.service;


import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.composition.TextObject;
import com.wltt.issuetree.global.apipayload.code.status.ErrorStatus;
import com.wltt.issuetree.global.apipayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

import java.io.IOException;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

@Service
@RequiredArgsConstructor
public class SlackbotService {
    private final MethodsClient methodsClient;

    public void chatMessage(
            final List<TextObject> textObjectList,
            final String headerContent,
            final String channelNameOrId
    ) {
        ChatPostMessageRequest messageRequest = ChatPostMessageRequest.builder()
                .channel(channelNameOrId)
                .blocks(asBlocks(
                                header(
                                        header -> header.text(plainText(headerContent))
                                ),
                                divider(),
                                section(section -> section.fields(textObjectList))
                        )
                ).build();
        try {
            methodsClient.chatPostMessage(messageRequest);
        } catch (SlackApiException | IOException e) {
            throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }


}
