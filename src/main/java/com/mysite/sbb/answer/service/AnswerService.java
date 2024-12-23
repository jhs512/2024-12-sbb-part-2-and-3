package com.mysite.sbb.answer.service;

import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.answer.repository.AnswerRepository;
import com.mysite.sbb.global.exceptions.DataNotFoundException;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public void createAnswer(Question question, String content, SiteUser user) {
        Answer answer = new Answer();
        answer.setQuestion(question);
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setAuthor(user);
        this.answerRepository.save(answer);
    }

    public void modifyAnswer(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id) {
        Optional<Answer> answerOptional = this.answerRepository.findById(id);
        if(answerOptional.isPresent()) {
            return answerOptional.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void deleteAnswer(Answer answer) {
        this.answerRepository.delete(answer);
    }

    public void voteAnswer(Answer answer, SiteUser siteUser) {
        answer.getVoter().add(siteUser);
        this.answerRepository.save(answer);
    }
}
