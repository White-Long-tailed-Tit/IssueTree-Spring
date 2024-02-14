package com.wltt.issuetree.report.service;

import com.wltt.issuetree.report.domain.repository.ReportRepository;
import com.wltt.issuetree.team.domain.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;
    private final TeamRepository teamRepository;
//    private final SlackbotService slackbotService;
//
//    @Transactional
//    public void reportIssue(ReportCreationRequest request) {
//        final Report report = request.toEntity();
//        reportRepository.save(report);
//        final Team team = teamRepository.findByPackageName(request.getPackageName());
//        slackbotService.sendSlackMessage(
//                "",
//                team.getChannelName()
//        );
//    }
}
