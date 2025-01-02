package com.mysite.sbb.domain.question.repository;

import com.mysite.sbb.domain.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Question findBySubject(String subject);
    Question findBySubjectAndContent(String subject, String content);
    List<Question> findBySubjectLike(String subject);
    @Query("SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.answerList")
    Page<Question> findAllWithAnswers(Pageable pageable);

    Page<Question> findAll(Specification<Question> spec, Pageable pageable);
}