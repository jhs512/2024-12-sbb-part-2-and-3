package com.ll.sbb.answer;

import com.ll.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public void create(String content, Question question) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        answerRepository.save(answer);
    }
}
