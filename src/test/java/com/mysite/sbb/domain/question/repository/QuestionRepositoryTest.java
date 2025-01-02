package com.mysite.sbb.domain.question.repository;

import com.mysite.sbb.domain.question.entity.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void setup() {
        questionRepository.deleteAll();
    }

    @Transactional
    @Test
    void t1() {
        Question q = Question.builder()
                .subject("스프링부트 모델 질문입니다.")
                .content("id는 자동으로 생성되나요?")
                .build();
        this.questionRepository.save(q);

        Optional<Question> oq = this.questionRepository.findById(q.getId());
        assertTrue(oq.isPresent());
        Question found = oq.get();
        assertEquals("스프링부트 모델 질문입니다.", found.getSubject());
        assertEquals("id는 자동으로 생성되나요?", found.getContent());
    }

    @Transactional
    @Test
    void t2() {
        Question q1 = Question.builder()
                .subject("스프링부트 질문입니다.")
                .content("id는 자동으로 생성되나요?")
                .build();
        this.questionRepository.save(q1);

        Question q2 = Question.builder()
                .subject("Spring Boot JPA 학습 질문입니다.")
                .content("엔티티 저장 방식이 궁금합니다.")
                .build();
        this.questionRepository.save(q2);

        List<Question> questions = this.questionRepository.findBySubjectLike("%질문%");
        assertEquals(2, questions.size());
    }
}