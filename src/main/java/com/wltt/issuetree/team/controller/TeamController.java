package com.wltt.issuetree.team.controller;

import com.wltt.issuetree.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
}
