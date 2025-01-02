package com.mysite.sbb.domain.answer.service;

import com.mysite.sbb.domain.answer.dto.AnswerDTO;
import com.mysite.sbb.domain.answer.entity.Answer;
import com.mysite.sbb.domain.answer.mapper.AnswerMapper;
import com.mysite.sbb.domain.answer.repository.AnswerRepository;
import com.mysite.sbb.domain.question.entity.Question;
import com.mysite.sbb.domain.question.repository.QuestionRepository;
import com.mysite.sbb.domain.user.entity.SiteUser;
import com.mysite.sbb.global.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final AnswerMapper answerMapper;

    @Transactional
    public AnswerDTO create(Long questionId, String content, SiteUser author) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new DataNotFoundException("question not found"));

        Answer answer = Answer.builder()
                .content(content)
                .question(question)
                .author(author)
                .build();

        question.addAnswer(answer);

        answerRepository.save(answer);
        return answerMapper.toDTO(answer);
    }

    public void delete(Long answerId) {
        this.answerRepository.deleteById(answerId);
    }

    public AnswerDTO getAnswer(Long answerId) {
        Optional<Answer> answer = this.answerRepository.findById(answerId);
        if (answer.isPresent()) {
            return answerMapper.toDTO(answer.get());
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    @Transactional
    public void modify(Long answerId, String content) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new DataNotFoundException("answer not found"));
        answer.modify(content);
        answer.onPreUpdate();
        answerRepository.save(answer);
    }

    public List<AnswerDTO> getListByQuestion(Long questionId) {
        return answerRepository.findAllByQuestion_Id(questionId)
                .stream()
                .map(answerMapper::toDTO)
                .toList();
    }

    @Transactional
    public void vote(Long answerId, SiteUser siteUser) {
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new DataNotFoundException("answer not found"));
        answer.vote(siteUser);
    }

    public Long getQuestionIdByAnswerId(Long answerId) {
        return answerRepository.findById(answerId)
                .map(Answer::getQuestionId)
                .orElseThrow(() -> new DataNotFoundException("answer not found"));
    }

}

