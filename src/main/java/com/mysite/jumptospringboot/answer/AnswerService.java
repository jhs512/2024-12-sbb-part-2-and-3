package com.mysite.jumptospringboot.answer;

import com.mysite.jumptospringboot.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content) { // Question이라는 Entity에 의존적일 필요가 있나? -> JPA를 이용하므로 엔터티에 의존
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answerRepository.save(answer);
    }
}
