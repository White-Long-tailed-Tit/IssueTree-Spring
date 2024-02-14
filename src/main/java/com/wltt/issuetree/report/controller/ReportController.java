package com.wltt.issuetree.report.controller;

import com.wltt.issuetree.report.request.ReportCreationRequest;
import com.wltt.issuetree.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("")
    public void reportIssue(
            @RequestBody final ReportCreationRequest request
    ) {
//        reportService.reportIssue(request);
    }
}
