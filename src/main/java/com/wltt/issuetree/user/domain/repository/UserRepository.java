package com.wltt.issuetree.user.domain.repository;

import com.wltt.issuetree.global.elasticsearch.repository.ElasticsearchRepository;
import com.wltt.issuetree.user.domain.User;

public interface UserRepository extends ElasticsearchRepository<User, String> {
}
