package com.wltt.issuetree.team.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Document(indexName = "teams")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Team {
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Keyword)
    private String channelId;

    @Field(type = FieldType.Keyword)
    private String channelName;

    @Field(type = FieldType.Nested)
    private List<String> roleList = new ArrayList<>();

    @Builder(builderMethodName = "of")
    public Team(String name, String channelId, String channelName, List<String> roleList) {
        this.name = name;
        this.channelId = channelId;
        this.channelName = channelName;
        this.roleList = roleList;
    }
}
