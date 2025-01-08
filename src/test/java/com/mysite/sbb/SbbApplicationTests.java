package com.mysite.sbb;

import com.mysite.sbb.Question.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Test
	void TestJpa() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트데이터:[03%d]", i);
			String content = "내용무";
			this.questionService.create(subject, content);
		}
	}
}
