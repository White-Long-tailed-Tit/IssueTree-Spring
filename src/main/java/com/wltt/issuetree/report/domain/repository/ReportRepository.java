package com.wltt.issuetree.report.domain.repository;

import com.wltt.issuetree.global.elasticsearch.repository.ElasticsearchRepository;
import com.wltt.issuetree.report.domain.Report;

public interface ReportRepository extends ElasticsearchRepository<Report, String> {
}
