package com.wltt.issuetree.report.service;

import com.wltt.issuetree.global.apipayload.code.status.ErrorStatus;
import com.wltt.issuetree.global.apipayload.exception.handler.NotFoundTeamException;
import com.wltt.issuetree.global.slasckbot.service.SlackbotService;
import com.wltt.issuetree.report.domain.Report;
import com.wltt.issuetree.report.domain.repository.ReportRepository;
import com.wltt.issuetree.report.request.ReportCreationRequest;
import com.wltt.issuetree.team.domain.Team;
import com.wltt.issuetree.team.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final TeamRepository teamRepository;
    private final SlackbotService slackbotService;

    public void reportIssue(ReportCreationRequest request) {
        final Report report = request.toEntity();
        reportRepository.save(report);
        final Team team = teamRepository.findByPackageListIsContaining(request.getPackageName())
                .orElseThrow(
                        () -> new NotFoundTeamException(ErrorStatus.FAIL_SEARCH_TEAM)
                );
        sendReportMessage(team, request);
    }

    private void sendReportMessage(Team team, ReportCreationRequest request) {
        String text
                = "*요청자:*\n" +
                ">" + request.getReporterName() + "\n\n" +
                "*스택 및 버전:*\n" +
                ">" + request.getStack() + "  " + request.getVersion() + "\n\n" +
                "*에러 메세지 전문:*\n" +
                "```" + request.getErrorMessage() + "```\n\n" +
                "*설명:*\n" +
                ">" + request.getComment() + "\n";

        String header = "새로운 오류 해결 요청이 들어왔습니다.";

        slackbotService.chatMessage(text, header, team.getChannelId());
    }
}
