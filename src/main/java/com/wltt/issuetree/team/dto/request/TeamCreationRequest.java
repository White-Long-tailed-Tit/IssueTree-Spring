package com.wltt.issuetree.team.dto.request;

import com.wltt.issuetree.team.domain.Team;
import lombok.Data;

import java.util.List;

@Data
public class TeamCreationRequest {
    private String currentChannelName;
    private String name;
    private String teamChannelId;
    private String teamChannelName;
    private List<String> roleList;

    public Team toEntity() {
        return Team.of()
                .name(name)
                .channelId(teamChannelId)
                .channelName(teamChannelName)
                .roleList(roleList)
                .build();
    }
}
