package com.wltt.issuetree.report.domain;

import com.wltt.issuetree.global.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import static lombok.AccessLevel.PROTECTED;

@Document(indexName = "reports")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Report extends BaseEntity {
    @Id
    private String id;

    private String reporterId;

    private String packageName;

    private String comment;

    @Builder(builderMethodName = "of")
    public Report(
            final String reporterId,
            final String packageName,
            final String comment
    ) {
        this.reporterId = reporterId;
        this.packageName = packageName;
        this.comment = comment;
    }

    @Override
    public boolean isNew() {
        return id == null || (getCreatedDate() == null);
    }
}
