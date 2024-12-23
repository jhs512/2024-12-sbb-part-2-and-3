package com.mysite.jumptospringboot.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

public interface QuestionRepositoryCustom {
    Page<Question> findAllByKeywordQDSL(@Param("kw") String kw, Pageable pageable);
}
