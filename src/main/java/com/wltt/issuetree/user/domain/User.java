package com.wltt.issuetree.user.domain;

import com.wltt.issuetree.global.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import static lombok.AccessLevel.PROTECTED;

@Document(indexName = "users")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class User extends BaseEntity {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    @Setter
    private String githubId;

    @Field(type = FieldType.Keyword)
    private String slackId;

    @Override
    public boolean isNew() {
        return id == null || (getCreatedDate() == null);
    }

    @Builder(builderMethodName = "of")
    public User(String githubId, String slackId) {
        this.githubId = githubId;
        this.slackId = slackId;
    }
}
