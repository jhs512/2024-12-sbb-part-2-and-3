package com.mysite.sbb.global.init;

import com.mysite.sbb.domain.answer.entity.Answer;
import com.mysite.sbb.domain.answer.repository.AnswerRepository;
import com.mysite.sbb.domain.question.entity.Question;
import com.mysite.sbb.domain.question.repository.QuestionRepository;
import com.mysite.sbb.domain.user.entity.SiteUser;
import com.mysite.sbb.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final UserService userService;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Bean
    public ApplicationRunner baseInitDataRunner() {
        return args -> {
            work();
        };
    }

    @Transactional
    public void work() {
        // 데이터가 이미 존재하면 초기화하지 않음
        if (questionRepository.count() > 0) return;

        SiteUser user1 = userService.create("user1", "user1@gmail.com", "1234");
        SiteUser user2 = userService.create("user2", "user2@gmail.com", "1234");

        // 첫 번째 질문 생성
        Question q1 = Question.builder()
                .subject("sbb가 무엇인가요?")
                .content("sbb에 대해서 알고 싶습니다.")
                .author(user1)
                .build();

        questionRepository.save(q1);

        Answer a1_1 = Answer.builder()
                .content("sbb는 Spring Boot Board의 약자입니다.")
                .question(q1)
                .author(user2)
                .build();

        Answer a1_2 = Answer.builder()
                .content("sbb는 간단한 게시판 예제 프로젝트입니다.")
                .question(q1)
                .author(user2)
                .build();

        answerRepository.save(a1_1);
        answerRepository.save(a1_2);

        q1.addAnswer(a1_1);
        q1.addAnswer(a1_2);

        Question q2 = Question.builder()
                .subject("스프링부트 모델 질문입니다.")
                .content("id는 자동으로 생성되나요?")
                .author(user2)
                .build();

        questionRepository.save(q2);

        Answer a2_1 = Answer.builder()
                .content("네, ID는 @GeneratedValue를 통해 자동 생성됩니다.")
                .question(q2)
                .author(user1)
                .build();

        Answer a2_2 = Answer.builder()
                .content("Spring Data JPA가 이를 처리해줍니다.")
                .question(q2)
                .author(user1)
                .build();

        answerRepository.save(a2_1);
        answerRepository.save(a2_2);

        q2.addAnswer(a2_1);
        q2.addAnswer(a2_2);
    }
}
