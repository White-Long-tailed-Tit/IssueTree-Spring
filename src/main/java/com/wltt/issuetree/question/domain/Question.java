package com.wltt.issuetree.question.domain;

import com.wltt.issuetree.global.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Document(indexName = "questions")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Question extends BaseEntity {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String stack;

    @Field(type = FieldType.Keyword)
    private String version;

    @Field(type = FieldType.Keyword)
    private String issue;

    @Field(type = FieldType.Keyword)
    private String questioner;

    @Field(type = FieldType.Keyword)
    private String solve;

    @Field(type = FieldType.Keyword)
    private String ts;

    @Builder
    public Question(String stack, String version, String issue, String questioner, String solve, String ts){
        this.stack = stack;
        this.version = version;
        this.issue = issue;
        this.questioner = questioner;
        this.solve = solve;
        this.ts = ts;
    }

    @Override
    public boolean isNew() {
        return id == null || (getCreatedDate() == null);
    }

}
