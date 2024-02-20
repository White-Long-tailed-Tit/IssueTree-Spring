package com.wltt.issuetree.question.domain.repository;

import com.wltt.issuetree.global.elasticsearch.repository.ElasticsearchRepository;
import com.wltt.issuetree.question.domain.Question;

import java.util.Optional;

public interface QuestionRepository extends ElasticsearchRepository<Question, Long> {
    boolean existsByTs(String ts);

    Optional<Question> findByTs(String ts);
}