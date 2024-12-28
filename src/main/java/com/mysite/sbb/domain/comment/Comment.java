package com.mysite.sbb.domain.comment;

import com.mysite.sbb.domain.answer.Answer;
import com.mysite.sbb.global.superclass.IdAndDate;
import com.mysite.sbb.domain.question.Question;
import com.mysite.sbb.domain.user.SiteUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends IdAndDate {
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    private Answer answer;

    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser author;

    @Builder
    public Comment(String content, Question question, Answer answer, SiteUser author){
        this.content=content;
        this.question=question;
        this.answer=answer;
        this.author=author;
    }

    public void updateContent(String content) {
        this.content=content;
    }
}
