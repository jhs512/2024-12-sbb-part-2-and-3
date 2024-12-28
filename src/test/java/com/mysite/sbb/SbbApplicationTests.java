package com.mysite.sbb;

import com.mysite.sbb.domain.question.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    QuestionService questionService;

}
