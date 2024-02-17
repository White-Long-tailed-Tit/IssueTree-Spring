package com.wltt.issuetree.report.domain;

import com.wltt.issuetree.global.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import static lombok.AccessLevel.PROTECTED;

@Document(indexName = "reports")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Report extends BaseEntity {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String reporter;

    @Field(type = FieldType.Keyword)
    private String packageName;

    @Field(type = FieldType.Text)
    private String errorMessage;

    @Field(type = FieldType.Text)
    private String comment;

    @Field(type = FieldType.Keyword)
    private String stack;

    @Field(type = FieldType.Keyword)
    private String version;

    @Builder(builderMethodName = "of")
    public Report(
            final String reporter,
            final String packageName,
            final String errorMessage,
            final String comment,
            final String stack,
            final String version
    ) {
        this.reporter = reporter;
        this.packageName = packageName;
        this.errorMessage = errorMessage;
        this.comment = comment;
        this.stack = stack;
        this.version = version;
    }

    @Override
    public boolean isNew() {
        return id == null || (getCreatedDate() == null);
    }
}
