package com.mysite.sbb;

import com.mysite.sbb.global.util.CommonUtil;
import com.mysite.sbb.question.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Test
	void testJpa() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용";
			this.questionService.createQuestion(subject, content, null);
		}
	}

	@Autowired
	private CommonUtil commonUtil;

	@Test
	void markdownTest() {
		String str = "# 안녕하세요";
		System.out.println(commonUtil.markdown(str));
	}
}
