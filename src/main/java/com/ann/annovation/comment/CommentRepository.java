package com.ann.annovation.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ann.annovation.answer.Answer;
import com.ann.annovation.question.Question;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByQuestionAndAnswerIsNull(Question question);
    List<Comment> findByAnswer(Answer answer);
}
