package com.mysite.jumptospringboot;

import com.mysite.jumptospringboot.answer.Answer;
import com.mysite.jumptospringboot.answer.AnswerRepository;
import com.mysite.jumptospringboot.question.Question;
import com.mysite.jumptospringboot.question.QuestionRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
@Rollback(value = false)
class JumpToSpringBootApplicationTests {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;


    @Test
    @Order(1)
    void testJpa1() {
        Question q1 = new Question();
        q1.setSubject("sbb가 무엇인가요?");
        q1.setContent("sbb에 대해서 알고 싶습니다.");
        q1.setCreateDate(LocalDateTime.now());
        questionRepository.save(q1); // SDJ에서는 따로 트랜잭션 처리를 안하면 기본 메서드 단위로 트랜잭션이 걸린다.

        Question q2 = new Question();
        q2.setSubject("스프링부트 모델 질문입니다.");
        q2.setContent("id는 자동으로 생성되나요?");
        q2.setCreateDate(LocalDateTime.now());
        questionRepository.save(q2); // 두번째 질문 저장.
    }

    @Test
    @Order(2)
    void testJpa2() {
        List<Question> questions = questionRepository.findAll();
        assertEquals(2, questions.size());

        Question q = questions.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
    }

    @Test
    @Order(3)
    void testJpa3() {
        Optional<Question> op = this.questionRepository.findById(1); // 반환값이 Optional
        if(op.isPresent()) {
            Question q = op.get();
            assertEquals("sbb가 무엇인가요?", q.getSubject());
        }
    }

    @Test
    @Order(4)
    void testJpa4() {
        Question q = questionRepository.findBySubject("sbb가 무엇인가요?"); // 이것도 Optional로 처리하는게 좋다.
        assertEquals(1, q.getId());
    }

    @Test
    @Order(5)
    void testJpa5() {

        Optional<Question> op = questionRepository.findBySubjectAndContent(
                "sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
        if(op.isPresent()) {
            Question q = op.get();
            assertEquals(1, q.getId());
        }
    }

    @Test
    @Order(6)
    void testJpa6() {
        List<Question> questions = questionRepository.findBySubjectLike("sbb%");
        assertEquals(1, questions.size());
        Question q = questions.get(0);
        assertEquals("sbb가 무엇인가요?", q.getSubject());
        assertEquals(1, q.getId());

    }

    @Test
    @Order(7)
    void testJpa7() {
        Optional<Question> oq = questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        q.setSubject("수정된 제목");
        questionRepository.save(q);
        // 메서드 단위로 트랜잭션을 한다면 이미 findById에서 영속성 컨텍스트에 들어가 있었으므로 save는 안써도 된다.
        // 그렇지만 save로 명시적인 제어를 해주는게 좋다
    }

    @Test
    @Order(8)
    void testJpa8() {
        assertEquals(2, questionRepository.count()); // COUNT(*) 쿼리. 성능 좋음.
        Optional<Question> oq = questionRepository.findById(1);
        assertTrue(oq.isPresent());
        Question q = oq.get();
        questionRepository.delete(q); // 준영속 상태라면 select 수행해서 해당 엔터티가 있는지 확인한다.
        // 근데 수행할 때 연관관계인것도 확인한다.
        assertEquals(1, questionRepository.count());
    }

    @Test
    @Order(9)
    void testJpa9() {
        Optional<Question> oq = questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q); // question_id가 테이블에 저장됨
        a.setCreateDate(LocalDateTime.now());
        answerRepository.save(a);
    }

    @Test
    @Order(10)
    void testJpa10() {
        // testJpa10. 여기선 DB에 값이 있음 - 여기도 없다. 영속성 컨텍스트에 있음.
        Optional<Answer> oa = answerRepository.findById(1); // eager
        assertTrue(oa.isPresent());
        Answer aa = oa.get();
        assertEquals(2, aa.getQuestion().getId());
    }

    @Test
//    @Transactional
    @Order(11)
    void testJpa11() {
        Optional<Question> oq = questionRepository.findById(2); // 트랜잭션 종료
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answers = q.getAnswers();

        assertEquals(1, answers.size());
        assertEquals("네 자동으로 생성됩니다.", answers.get(0).getContent());
    }
}
