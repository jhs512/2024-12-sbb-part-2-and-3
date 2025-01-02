package com.mysite.sbb.domain.answer.entity;

import com.mysite.sbb.domain.question.entity.Question;
import com.mysite.sbb.domain.user.entity.SiteUser;
import com.mysite.sbb.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Answer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    Set<SiteUser> voter;

    /**
     * 비즈니스 로직
     */
    public void vote(SiteUser siteUser) {
        this.voter.add(siteUser);
    }

    public Long getQuestionId() {
        return this.question.getId();
    }

    /**
     * 연관관계 편의 메서드
     */
    public void linkQuestion(Question question) {
        this.question = question;
    }

    public void modify(String content){
        this.content = content;
    }
}