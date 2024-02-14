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
    private Long id;

    private String reporterName;

    private String reporterTeamName;

    private String packageName;

    private String comment;

    @Builder(builderMethodName = "of")
    public Report(
            final String reporterName,
            final String reporterTeamName,
            final String packageName,
            final String comment
    ) {
        this.reporterName = reporterName;
        this.reporterTeamName = reporterTeamName;
        this.packageName = packageName;
        this.comment = comment;
    }
}
