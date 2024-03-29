package com.wltt.issuetree.team.service;

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
import java.util.Optional;

import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final SlackbotService slackbotService;

    public void addTeam(SlackUrlEncodedForm request) {
        final String channelId = TeamParser.parseChannelId(request.getText());
        final Optional<Team> optionalTeam = teamRepository.findByChannelId(channelId);

        Team team;
        if (optionalTeam.isPresent()) {
            final List<String> packageList = TeamParser.parsePackageList(request.getText());
            team = optionalTeam.get();
            team.getPackageList().addAll(packageList);
        } else {
            team = TeamParser.parseToTeam(request);
        }
        teamRepository.save(team);
        chatSuccessMessage(request, team);
    }

    public void searchTeam(SlackUrlEncodedForm request) {
        final String channelId = TeamParser.parseChannelId(request.getText());
        final Optional<Team> optionalTeam = teamRepository.findByChannelId(channelId);

        Team team;
        if (optionalTeam.isPresent()) {
            team = optionalTeam.get();
            chatSearchMessage(request, team);
        } else {
            chatFailSearchTeamMessage(request);
        }
    }

    public void deleteTeam(SlackUrlEncodedForm request) {
        final String channelId = TeamParser.parseChannelId(request.getText());
        final List<String> packageList = TeamParser.parsePackageList(request.getText());
        final Optional<Team> optionalTeam = teamRepository.findByChannelId(channelId);

        Team team;
        if (optionalTeam.isPresent()) {
            team = optionalTeam.get();
            team.getPackageList().removeAll(packageList);
            teamRepository.save(team);
            chatChangeRoleMessage(request, team);
        } else {
            chatFailSearchTeamMessage(request);
        }
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

    private void chatSearchMessage(
            final SlackUrlEncodedForm request,
            final Team team
    ) {
        List<TextObject> textObjects = new ArrayList<>();
        textObjects.add(markdownText("*요청자:*\n <@" + request.getUserId() + ">"));
        textObjects.add(markdownText("*팀채널:*\n <#" + team.getChannelId() + ">"));

        final List<String> packageList = team.getPackageList();
        StringBuilder text = new StringBuilder();
        for (String p : packageList) {
            text.append("• ").append(p).append("\n");
        }
        textObjects.add(markdownText("*현재 역할:*\n" + text));

        String header = "역할 확인";

        slackbotService.chatMessage(
                textObjects,
                header,
                request.getChannelId()
        );
    }

    private void chatChangeRoleMessage(
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


        String header = "정상적으로 역할이 변경되었습니다.";

        slackbotService.chatMessage(
                textObjects,
                header,
                request.getChannelName()
        );

        header = "역할이 변경되었습니다.";

        slackbotService.chatMessage(
                textObjects,
                header,
                team.getChannelId()
        );
    }

    private void chatFailSearchTeamMessage(
            final SlackUrlEncodedForm request
    ) {
        String header = "팀 채널 검색 실패";
        String content = "해당하는 팀 채널에 배정된 역할이 없습니다.";
        slackbotService.chatMessage(
                content,
                header,
                request.getChannelId()
        );
    }
}
