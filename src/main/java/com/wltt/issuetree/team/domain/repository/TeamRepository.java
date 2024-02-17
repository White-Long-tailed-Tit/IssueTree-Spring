package com.wltt.issuetree.team.domain.repository;

import com.wltt.issuetree.global.elasticsearch.repository.ElasticsearchRepository;
import com.wltt.issuetree.team.domain.Team;
import org.springframework.data.elasticsearch.annotations.Query;

import java.util.Optional;

public interface TeamRepository extends ElasticsearchRepository<Team, String> {
    // TODO: 쿼리 메소드로 해결하였으나, 좀 더 확실하게 @Query 어노테이션 붙여서 해결해보기
    Optional<Team> findByPackageListIsContaining(String packageName);
}
