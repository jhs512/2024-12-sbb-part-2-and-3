package com.ll.sbb;

import com.ll.sbb.answer.Answer;
import com.ll.sbb.answer.AnswerRepository;
import com.ll.sbb.question.Question;
import com.ll.sbb.question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test
	void t1() {
		Question question1 = new Question();
		question1.setSubject("sbb가 무엇인가요?");
		question1.setContent("sbb에 대해서 알고 싶습니다.");
		question1.setCreateDate(LocalDateTime.now());
		questionRepository.save(question1);

		Question question2 = new Question();
		question2.setSubject("스프링부트 모델 질문입니다.");
		question2.setContent("id는 자동으로 생성되나요?");
		question2.setCreateDate(LocalDateTime.now());
		questionRepository.save(question2);
	}

	@Test
	void t2() {
		List<Question> all = questionRepository.findAll();
		assertEquals(2, all.size());
	}

	@Test
	void t3(){
		Optional<Question> op = questionRepository.findById(1L);
		if (op.isPresent()) {
			Question question = op.get();
			assertEquals("sbb가 무엇인가요?", question.getSubject());
		}
	}

	@Test
	void t4() {
		Question question = questionRepository.findBySubject("sbb가 무엇인가요?");
		assertEquals(1, question.getId());
	}

	@Test
	void t5() {
		Question question = questionRepository.findBySubjectAndContent("sbb가 무엇인가요?", "sbb에 대해서 알고 싶습니다.");
		assertEquals(1, question.getId());
	}

	@Test
	void t6() {
		List<Question> list = questionRepository.findBySubjectLike("sbb%");
		Question question = list.get(0);
		assertEquals("sbb가 무엇인가요?", question.getSubject());
	}

	@Test
	void t7() {
		Question question1 = new Question();
		question1.setSubject("수정 테스트");
		question1.setContent("수정 테스트용");
		question1.setCreateDate(LocalDateTime.now());
		questionRepository.save(question1);

		Optional<Question> op = questionRepository.findById(3L);
		assertTrue(op.isPresent());

		Question question = op.get();
		question.setSubject("수정된 제목");
		questionRepository.save(question);
	}

	@Test
	void t8() {
		Question question1 = new Question();
		question1.setSubject("삭제 테스트");
		question1.setContent("삭제 테스트용");
		question1.setCreateDate(LocalDateTime.now());
		questionRepository.save(question1);

		questionRepository.delete(question1);

		assertEquals(3, questionRepository.count());
	}

	@Test
	void t9() {
		Optional<Question> op = questionRepository.findById(2L);
		assertTrue(op.isPresent());
		Question question = op.get();

		Answer answer = new Answer();
		answer.setContent("네 자동으로 생성됩니다.");
		answer.setQuestion(question);
		answer.setCreateDate(LocalDateTime.now());

		answerRepository.save(answer);
	}

	@Test
	void t10() {
		Optional<Answer> op = answerRepository.findById(1L);
		assertTrue(op.isPresent());
		Answer answer = op.get();
		assertEquals(2, answer.getQuestion().getId());
	}

	@Test
	@Transactional
	void t11() {
		Optional<Question> op = questionRepository.findById(2L);
		assertTrue(op.isPresent());
		Question question = op.get();

		List<Answer> answerList = question.getAnswerList();
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}

}
