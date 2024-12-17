package com.mysite.jumptospringboot.answer;

import com.mysite.jumptospringboot.DataNotFoundException;
import com.mysite.jumptospringboot.question.Question;
import com.mysite.jumptospringboot.user.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void create(Question question, String content, SiteUser author) { // Question이라는 Entity에 의존적일 필요가 있나? -> JPA를 이용하므로 엔터티에 의존. (연관관계를 엔터티로 하잖아)
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        answer.setAuthor(author);
        answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id) {
        return answerRepository.findById(id).orElseThrow(()->new DataNotFoundException("answer not found"));
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        answerRepository.save(answer);
    }
}
