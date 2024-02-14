package com.wltt.issuetree.team.service;

import com.wltt.issuetree.global.slasckbot.service.SlackbotService;
import com.wltt.issuetree.team.domain.Team;
import com.wltt.issuetree.team.domain.repository.TeamRepository;
import com.wltt.issuetree.team.dto.request.TeamCreationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final SlackbotService slackbotService;

    public void addTeam(TeamCreationRequest request) {
        final Team team = request.toEntity();
        teamRepository.save(team);
        slackbotService.sendSlackMessage("팀의 역할이 성공적으로 부여되었습니다.", request.getCurrentChannelName());
    }
}
