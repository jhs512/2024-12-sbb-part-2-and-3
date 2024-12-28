package com.mysite.sbb.domain.answer;

import com.mysite.sbb.domain.comment.Comment;
import com.mysite.sbb.global.superclass.IdAndDate;
import com.mysite.sbb.domain.question.Question;
import com.mysite.sbb.domain.user.SiteUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Answer extends IdAndDate {
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    private Set<SiteUser> voter;

    @OneToMany(mappedBy = "answer")
    private List<Comment> commentList;

    @Builder
    public Answer(String content, Question question, SiteUser author){
        this.content=content;
        this.question=question;
        this.author=author;
    }
    public void updateContent(String content){this.content=content;}
}
