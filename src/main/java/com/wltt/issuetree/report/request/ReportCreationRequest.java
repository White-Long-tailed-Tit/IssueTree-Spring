package com.wltt.issuetree.report.request;

import com.wltt.issuetree.report.domain.Report;
import lombok.Data;

@Data
public class ReportCreationRequest {
    private String reporterName;
    private String reporterTeamName;
    private String packageName;
    private String comment;

    public Report toEntity() {
        return Report.of()
                .reporterName(reporterName)
                .reporterTeamName(reporterTeamName)
                .packageName(packageName)
                .comment(comment)
                .build();
    }
}
