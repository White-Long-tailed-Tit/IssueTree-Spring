package com.wltt.issuetree.user.domain.repository;

import com.wltt.issuetree.global.elasticsearch.repository.ElasticsearchRepository;
import com.wltt.issuetree.user.domain.User;

import java.util.Optional;

public interface UserRepository extends ElasticsearchRepository<User, String> {
    Optional<User> findByGithubId(String githubId);

    Optional<User> findBySlackId(String slackId);
}
