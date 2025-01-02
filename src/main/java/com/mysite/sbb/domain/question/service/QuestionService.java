package com.mysite.sbb.domain.question.service;

import com.mysite.sbb.domain.answer.entity.Answer;
import com.mysite.sbb.domain.question.dto.QuestionDTO;
import com.mysite.sbb.domain.question.entity.Question;
import com.mysite.sbb.domain.question.mapper.QuestionMapper;
import com.mysite.sbb.domain.question.repository.QuestionRepository;
import com.mysite.sbb.domain.user.entity.SiteUser;
import com.mysite.sbb.global.exception.DataNotFoundException;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Transactional
    public void create(String subject, String content, SiteUser user) {
        Question question = Question.builder()
                .subject(subject)
                .content(content)
                .author(user)
                .build();

        questionRepository.save(question);
    }

    public Long count() {
        return questionRepository.count();
    }

    @Transactional
    public void modify(Long questionId, String subject, String content) {
        Question question = questionRepository.findById(questionId).orElseThrow(
                () -> new DataNotFoundException("question not found"));
        question.setSubject(subject);
        question.setContent(content);
        question.onPreUpdate();

        questionRepository.save(question);
    }

//    public List<QuestionDTO> getList() {
//        return questionRepository.findAll()
//                .stream()
//                .map(questionMapper::toDTO)
//                .toList();
//    }

    public Page<QuestionDTO> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Specification<Question> spec = search(kw);
        return questionRepository.findAll(spec,pageable)
                .map(questionMapper::toDTO);
    }

    public QuestionDTO getQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new DataNotFoundException("question not found"));
        return questionMapper.toDTO(question);
    }

    @Transactional
    public void delete(Long id) {
        this.questionRepository.deleteById(id);
    }

    @Transactional
    public void vote(Long questionId, SiteUser siteUser) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new DataNotFoundException("question not found"));

        question.getVoter().add(siteUser);
//        this.questionRepository.save(question);
    }

    private Specification<Question> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            @Override
            public Predicate toPredicate(Root<Question> q, CriteriaQuery<?> query, CriteriaBuilder cb) {
                query.distinct(true);  // 중복을 제거
                Join<Question, SiteUser> u1 = q.join("author", JoinType.LEFT);
                Join<Question, Answer> a = q.join("answerList", JoinType.LEFT);
                Join<Answer, SiteUser> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + kw + "%"), // 제목
                        cb.like(q.get("content"), "%" + kw + "%"),      // 내용
                        cb.like(u1.get("username"), "%" + kw + "%"),    // 질문 작성자
                        cb.like(a.get("content"), "%" + kw + "%"),      // 답변 내용
                        cb.like(u2.get("username"), "%" + kw + "%"));   // 답변 작성자
            }
        };
    }

}
