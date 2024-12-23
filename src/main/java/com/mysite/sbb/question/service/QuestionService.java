package com.mysite.sbb.question.service;

import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.global.exceptions.DataNotFoundException;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.repository.QuestionRepository;
import com.mysite.sbb.user.entity.SiteUser;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    private Specification<Question> search(String kw) {
        return new Specification<>() {
            @Override
            public Predicate toPredicate(Root<Question> question, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);
                Join<Question, SiteUser> questionAuthor = question.join("author", JoinType.LEFT);
                Join<Question, Answer> answer = question.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> answerAuthor = answer.join("author", JoinType.LEFT);
                return cb.or(
                        cb.like(question.get("subject"), "%" + kw + "%"),
                        cb.like(question.get("content"), "%" + kw + "%"),
                        cb.like(questionAuthor.get("username"), "%" + kw + "%"),
                        cb.like(answer.get("content"), "%" + kw + "%"),
                        cb.like(answerAuthor.get("username"), "%" + kw + "%")
                );
            }
        };
    }

    public Page<Question> getList(int page, String kw) {
        List<Sort.Order> sorting = new ArrayList<>();
        sorting.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorting));
        Specification<Question> specification = search(kw);
        return questionRepository.findAll(specification, pageable);
    }

    public Question getQuestion(Integer id) {
        Optional<Question> questionOptional = this.questionRepository.findById(id);
        if(questionOptional.isPresent()){
            return questionOptional.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public Question createQuestion(String subject, String content, SiteUser user) {
        Question question = new Question();
        question.setSubject(subject);
        question.setContent(content);
        question.setCreateDate(LocalDateTime.now());
        question.setAuthor(user);
        this.questionRepository.save(question);
        return question;
    }

    public void modifyQuestion(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
        question.setModifyDate(LocalDateTime.now());
        this.questionRepository.save(question);
    }

    public void deleteQuestion(Question question) {
        this.questionRepository.delete(question);
    }

    public void voteQuestion(Question question, SiteUser siteUser) {
        question.getVoter().add(siteUser);
        this.questionRepository.save(question);
    }
}
