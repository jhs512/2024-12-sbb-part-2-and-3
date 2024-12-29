package com.ll.sbb.answer;

import com.ll.sbb.DataNotFoundException;
import com.ll.sbb.question.Question;
import com.ll.sbb.user.SiteUser;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerRepository answerRepository;

    public Answer create(String content, Question question, SiteUser user) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setQuestion(question);
        answer.setCreateDate(LocalDateTime.now());
        answer.setUser(user);

        answerRepository.save(answer);

        return answer;
    }

    public Answer getAnswer(long id) {
        Optional<Answer> op = answerRepository.findById(id);
        op.orElseThrow(() -> new DataNotFoundException("answer not found"));
        return op.get();
    }

    public void modify(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        answerRepository.save(answer);
    }

    public void delete(Answer answer) {
        answerRepository.delete(answer);
    }

    public void vote(Answer answer, SiteUser user) {
        answer.getVoter().add(user);
        answerRepository.save(answer);
    }
}
