package com.wltt.issuetree.team.domain.repository;

import com.wltt.issuetree.global.elasticsearch.repository.ElasticsearchRepository;
import com.wltt.issuetree.team.domain.Team;
import org.springframework.data.elasticsearch.annotations.Query;

import java.util.Optional;

public interface TeamRepository extends ElasticsearchRepository<Team, String> {
    Optional<Team> findByPackageListIsContaining(String packageName);
}
