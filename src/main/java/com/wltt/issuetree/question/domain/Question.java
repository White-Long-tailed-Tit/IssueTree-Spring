package com.wltt.issuetree.question.domain;

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
public class Question {
    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String stack;

    @Field(type = FieldType.Keyword)
    private String version;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSZZ")
    private LocalDateTime createdDate;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSZZ")
    private LocalDateTime lastModifiedDate;

    @Field(type = FieldType.Nested)
    private List<String> messageList = new ArrayList<>();
}
