package com.wltt.issuetree.team.service;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.composition.TextObject;
import com.wltt.issuetree.global.SlackUrlEncodedForm;
import com.wltt.issuetree.global.apipayload.code.status.ErrorStatus;
import com.wltt.issuetree.global.apipayload.exception.GeneralException;
import com.wltt.issuetree.team.domain.Team;
import com.wltt.issuetree.team.domain.repository.TeamRepository;
import com.wltt.issuetree.team.parser.TeamParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final MethodsClient methodsClient;

    public void addTeam(SlackUrlEncodedForm request) {
        final Team team = TeamParser.parseToTeam(request);
        teamRepository.save(team);

        // Slack 메세지 보내기
        try {
            List<TextObject> textObjects = new ArrayList<>();
            textObjects.add(markdownText("*팀채널:*\n <#" + team.getChannelId() + ">"));

            final List<String> packageList = team.getPackageList();
            StringBuilder text = new StringBuilder();
            for (String p : packageList) {
                text.append("- ").append(p).append("\n");
            }
            textObjects.add(markdownText("*배정된 역할:*\n" + text));

            System.out.println("채널 이름 확인 ::::: " + request.getChannelName());

            ChatPostMessageRequest messageRequest = ChatPostMessageRequest.builder()
                    .channel(request.getChannelName())
                    .blocks(asBlocks(
                                    header(
                                            header -> header.text(plainText("<@" + request.getUserId() + ">님이 요청한 역할이 정상적으로 배정되었습니다"))
                                    ),
                                    divider(),
                                    section(section -> section.fields(textObjects))
                            )
                    ).build();
            methodsClient.chatPostMessage(messageRequest);

        } catch (SlackApiException | IOException e) {
            System.out.println(e);
            new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
        }
    }
}
