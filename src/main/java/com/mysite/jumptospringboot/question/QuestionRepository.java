package com.mysite.jumptospringboot.question;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findBySubject(String subject);
    Optional<Question> findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject); // JPA에 더 가까운 방법은 containing
    List<Question> findBySubjectContaining(String subject); // 이게 더 JPA스타일이다. 자동으로 SQL 쿼리를 최적화 하므로 실수를 줄일 수 있따.
    Page<Question> findAll(Pageable pageable);
}
