package com.wltt.issuetree.report.service;

import com.wltt.issuetree.global.slasckbot.service.SlackbotService;
import com.wltt.issuetree.report.domain.Report;
import com.wltt.issuetree.report.domain.repository.ReportRepository;
import com.wltt.issuetree.report.request.ReportCreationRequest;
import com.wltt.issuetree.team.domain.Team;
import com.wltt.issuetree.team.domain.repository.TeamRepository;
import com.wltt.issuetree.user.domain.User;
import com.wltt.issuetree.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final SlackbotService slackbotService;

    private static final String MAIN_CHANNEL_ID = "C06L3MMDKUJ";

    public void reportIssue(ReportCreationRequest request) {
        final Report report = request.toEntity();
        reportRepository.save(report);

        Optional<User> optionalUser = Optional.empty();
        Optional<Team> optionalTeam = Optional.empty();

        if (request.getManagerGithubId() != null) {
            optionalUser = userRepository.findByGithubId(request.getManagerGithubId());
        }
        if (request.getPackageName() != null) {
            optionalTeam = teamRepository.findByPackageListIsContaining(request.getPackageName());
        }
        sendReportMessage(optionalUser, optionalTeam, request);
    }

    private void sendReportMessage(
            final Optional<User> user,
            final Optional<Team> team,
            final ReportCreationRequest request
    ) {
        String text
                = "*요청자:*\n" +
                ">" + request.getReporterName() + "\n";

        if (user.isPresent()) {
            text += "*담당자:*\n" +
                    "><@" + user.get().getSlackId() + ">\n";
        }

        text += "*스택 및 버전:*\n" +
                ">" + request.getStack() + "  " + request.getVersion() + "\n" +
                "*에러 메세지 전문:*\n" +
                "```" + request.getErrorMessage() + "```\n" +
                "*설명:*\n" +
                ">" + request.getComment() + "\n";

        String header = "새로운 오류 해결 요청이 들어왔습니다.";

        if (team.isPresent()) {
            slackbotService.chatMessage(text, header, team.get().getChannelId());
        }
        if (user.isPresent()) {
            slackbotService.chatMessage(text, header, user.get().getSlackId());
        }
        if (!team.isPresent() && !user.isPresent()) {
            sendToAll(text, "담당자를 찾지 못한 오류의 해결 요청이 들어왔습니다.");
        }
    }

    private void sendToAll(
            String content,
            String header
    ) {
        String text = "<!here> \n" + content;
        slackbotService.chatMessage(text, header, MAIN_CHANNEL_ID);
    }
}
