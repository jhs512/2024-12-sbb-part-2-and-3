package com.mysite.sbb.domain.question;

import com.mysite.sbb.domain.answer.Answer;
import com.mysite.sbb.domain.category.Category;
import com.mysite.sbb.domain.comment.Comment;
import com.mysite.sbb.domain.user.SiteUser;
import com.mysite.sbb.global.superclass.IdAndDate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends IdAndDate {
    @Column(length=200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answerList;

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    private Set<SiteUser> voter;

    @OneToMany(mappedBy = "question")
    private List<Comment> commentList;

    @ManyToOne
    private Category category;

    @ColumnDefault("0")
    private long visited;

    @Builder
    public Question(String subject, String content, SiteUser author, Category category){
        this.subject = subject;
        this.content = content;
        this.author = author;
        this.category = category;
    }

    public void updateSubject(String subject){this.subject=subject;}
    public void updateContent(String content){this.content=content;}
    public void updateVisited(){this.visited=this.visited+1;}
}
