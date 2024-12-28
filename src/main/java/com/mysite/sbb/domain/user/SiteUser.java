package com.mysite.sbb.domain.user;


import com.mysite.sbb.domain.answer.Answer;
import com.mysite.sbb.domain.comment.Comment;
import com.mysite.sbb.domain.question.Question;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SiteUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique=true)
    private String email;

    @Builder
    public SiteUser(String username, String password, String email){
        this.username=username;
        this.password=password;
        this.email=email;
    }

    @OneToMany(mappedBy = "author")
    private List<Question> questions;

    @OneToMany(mappedBy = "author")
    private List<Answer> answers;

    @OneToMany(mappedBy = "author")
    private List<Comment> comments;

    public void updatePassword(String password) {
        this.password=password;
    }
}
