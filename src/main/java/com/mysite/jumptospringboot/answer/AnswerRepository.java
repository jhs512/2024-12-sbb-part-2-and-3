package com.mysite.jumptospringboot.answer;

import com.mysite.jumptospringboot.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    Page<Answer>findByQuestion(Question question, Pageable pageable);
    Page<Answer> findByQuestionId(Integer questionId, Pageable pageable);
}
