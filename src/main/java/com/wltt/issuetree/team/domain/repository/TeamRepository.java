package com.wltt.issuetree.team.domain.repository;

import com.wltt.issuetree.global.elasticsearch.repository.ElasticsearchRepository;
import com.wltt.issuetree.team.domain.Team;
import org.springframework.data.elasticsearch.annotations.Query;

public interface TeamRepository extends ElasticsearchRepository<Team, Long> {
    @Query("") // TODO: 쿼리 작성하기
    Team findByPackageName(String packageName);
}
