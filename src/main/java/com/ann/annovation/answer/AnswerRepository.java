package com.ann.annovation.answer;

import com.ann.annovation.question.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    // 특정 Question 게시물의 Answer만을 뽑아 Paging
    Page<Answer> findByQuestion(Question question, Pageable pageable);
}
