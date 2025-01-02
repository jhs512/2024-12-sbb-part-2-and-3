package com.mysite.sbb.domain.answer.repository;

import com.mysite.sbb.domain.answer.entity.Answer;
import com.mysite.sbb.domain.question.entity.Question;
import com.mysite.sbb.domain.question.repository.QuestionRepository;
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
class AnswerRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @BeforeEach
    void setup() {
        questionRepository.deleteAll();
        answerRepository.deleteAll();
    }

    @Transactional
    @Test
    void t1() {
        Question q = Question.builder()
                .subject("스프링부트 모델 질문입니다.")
                .content("id는 자동으로 생성되나요?")
                .build();
        this.questionRepository.save(q);

        Answer a = Answer.builder()
                .content("네 자동으로 생성됩니다.")
                .build();
        q.addAnswer(a);
        this.answerRepository.save(a);

        Optional<Answer> oa = this.answerRepository.findById(a.getId());
        assertTrue(oa.isPresent());
        Answer found = oa.get();
        assertEquals("네 자동으로 생성됩니다.", found.getContent());
        assertEquals(q.getId(), found.getQuestion().getId());
    }

    @Transactional
    @Test
    void t2() {
        Question q = Question.builder()
                .subject("스프링부트 모델 질문입니다.")
                .content("id는 자동으로 생성되나요?")
                .build();
        this.questionRepository.save(q);

        Answer a1 = Answer.builder()
                .content("네 자동으로 생성됩니다.")
                .build();
        q.addAnswer(a1); // 자동으로 관계 설정
        this.answerRepository.save(a1);

        Answer a2 = Answer.builder()
                .content("이 질문은 매우 중요합니다.")
                .build();
        q.addAnswer(a2); // 자동으로 관계 설정
        this.answerRepository.save(a2);

        Optional<Question> oq = this.questionRepository.findById(q.getId());
        assertTrue(oq.isPresent());
        Question foundQuestion = oq.get();

        List<Answer> answers = foundQuestion.getAnswerList();
        assertEquals(2, answers.size());
        assertEquals("네 자동으로 생성됩니다.", answers.get(0).getContent());
        assertEquals("이 질문은 매우 중요합니다.", answers.get(1).getContent());
    }

}
