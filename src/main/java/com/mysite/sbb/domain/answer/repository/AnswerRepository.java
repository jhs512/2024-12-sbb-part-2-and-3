package com.mysite.sbb.domain.answer.repository;

import com.mysite.sbb.domain.answer.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findAllByQuestion_Id(Long id);
}