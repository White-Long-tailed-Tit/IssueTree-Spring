package com.wltt.issuetree.team.domain.repository;

import com.wltt.issuetree.global.elasticsearch.repository.ElasticsearchRepository;
import com.wltt.issuetree.team.domain.Team;

public interface TeamRepository extends ElasticsearchRepository<Team, Long> {
}
