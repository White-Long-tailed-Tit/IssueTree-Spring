package com.wltt.issuetree.report.request;

import com.wltt.issuetree.report.domain.Report;
import lombok.Data;

@Data
public class ReportCreationRequest {
    private String reporterId;
    private String packageName;
    private String comment;

    public Report toEntity() {
        return Report.of()
                .reporterId(reporterId)
                .packageName(packageName)
                .comment(comment)
                .build();
    }
}
