package com.wltt.issuetree.report.request;

import com.wltt.issuetree.report.domain.Report;
import lombok.Data;

@Data
public class ReportCreationRequest {
    private String reporterName;
    private String packageName;
    private String errorMessage;
    private String comment;
    private String stack;
    private String version;

    public Report toEntity() {
        return Report.of()
                .stack(stack)
                .errorMessage(errorMessage)
                .version(version)
                .packageName(packageName)
                .comment(comment)
                .build();
    }
}
