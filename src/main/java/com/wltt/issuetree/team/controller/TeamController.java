package com.wltt.issuetree.team.controller;

import com.wltt.issuetree.global.SlackUrlEncodedForm;
import com.wltt.issuetree.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/teams")
public class TeamController {
    private final TeamService teamService;

    @PostMapping(
            value = "",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void addTeams(
            @RequestBody final MultiValueMap<String, Object> data
    ) {
        System.out.println(data);
        teamService.addTeam(new SlackUrlEncodedForm(data));
    }
}
