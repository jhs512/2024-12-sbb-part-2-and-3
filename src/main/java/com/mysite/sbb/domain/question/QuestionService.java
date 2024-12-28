package com.mysite.sbb.domain.question;

import com.mysite.sbb.domain.category.Category;
import com.mysite.sbb.domain.user.SiteUser;
import com.mysite.sbb.global.exception.DataNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;

    public Page<Question> getList(int page, String kw){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return questionRepository.findAllByKeyword(kw,pageable);
    }

    public Page<Question> getCategoryList(int page, int categoryId){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10, Sort.by(sorts));
        return questionRepository.findAllByCategoryId(categoryId, pageable);
    }

    public Question create(String subject, String content, SiteUser author, Category category){
        Question q = Question.builder()
                .subject(subject)
                .content(content)
                .author(author)
                .category(category)
                .build();
        questionRepository.save(q);
        return q;
    }

    public Question getQuestion(Integer id){
        Optional<Question> _question = questionRepository.findById(id);
        if(_question.isPresent()){
            return _question.get();
        }else{
            throw new DataNotFoundException("question not found");
        }
    }

    public void modify(Question question, String subject, String content){
        question.updateSubject(subject);
        question.updateContent(content);
        questionRepository.save(question);
    }

    public void delete(Question question){
        questionRepository.delete(question);
    }

    public void vote(Question question, SiteUser siteUser){
        question.getVoter().add(siteUser);
        questionRepository.save(question);
    }

    public long count() {
        return questionRepository.count();
    }

    public void visited(Question question){
        question.updateVisited();
        questionRepository.save(question);
    }
}
