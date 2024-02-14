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
    private String channelId;

    @Field(type = FieldType.Keyword)
    private List<String> packageList = new ArrayList<>();

    @Builder(builderMethodName = "of")
    public Team(
            final String channelId,
            final List<String> packageList
    ) {
        this.channelId = channelId;
        this.packageList = packageList;
    }
}
