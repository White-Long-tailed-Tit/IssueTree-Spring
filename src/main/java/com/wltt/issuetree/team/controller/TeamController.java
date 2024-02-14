package com.wltt.issuetree.team.controller;

import com.wltt.issuetree.team.dto.request.TeamCreationRequest;
import com.wltt.issuetree.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {
    private final TeamService teamService;

    @PostMapping("")
    public void addTeams(
            @RequestBody TeamCreationRequest request
    ) {
        teamService.addTeam(request);
    }
}
