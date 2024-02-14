package com.wltt.issuetree.team.service;

import com.slack.api.methods.MethodsClient;
import com.slack.api.model.block.composition.TextObject;
import com.wltt.issuetree.global.SlackUrlEncodedForm;
import com.wltt.issuetree.global.slasckbot.service.SlackbotService;
import com.wltt.issuetree.team.domain.Team;
import com.wltt.issuetree.team.domain.repository.TeamRepository;
import com.wltt.issuetree.team.parser.TeamParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final MethodsClient methodsClient;
    private final SlackbotService slackbotService;

    public void addTeam(SlackUrlEncodedForm request) {
        final Team team = TeamParser.parseToTeam(request);
        teamRepository.save(team);
        chatSuccessMessage(request, team);
    }

    private void chatSuccessMessage(
            final SlackUrlEncodedForm request,
            final Team team) {

        List<TextObject> textObjects = new ArrayList<>();
        textObjects.add(markdownText("*요청자:*\n <@" + request.getUserId() + ">"));
        textObjects.add(markdownText("*팀채널:*\n <#" + team.getChannelId() + ">"));

        final List<String> packageList = team.getPackageList();
        StringBuilder text = new StringBuilder();
        for (String p : packageList) {
            text.append("• ").append(p).append("\n");
        }
        textObjects.add(markdownText("*배정된 역할:*\n" + text));


        String header = "정상적으로 역할이 지정되었습니다.";

        slackbotService.chatMessage(
                textObjects,
                header,
                request.getChannelName()
        );

        header = "새로운 역할이 지정되었습니다.";

        slackbotService.chatMessage(
                textObjects,
                header,
                team.getChannelId()
        );
    }
}
