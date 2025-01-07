package com.mysite.sbb;

import com.mysite.sbb.Question.Question;
import com.mysite.sbb.Question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void TestJpa() {
		// 첫번 째 질문 저장
		Question q1 = new Question();
		q1.setSubject("sbb가 뭔고?");
		q1.setContent("sbb가 궁금합니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		// 두번 째 질문 저장
		Question q2 = new Question();
		q2.setSubject("스프링부트 모델 질문임");
		q2.setContent("자동으로 id 생성됨?");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}
}
