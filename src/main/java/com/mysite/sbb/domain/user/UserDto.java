package com.mysite.sbb.domain.user;

import com.mysite.sbb.domain.answer.Answer;
import com.mysite.sbb.domain.comment.Comment;
import com.mysite.sbb.domain.question.Question;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String username;
    private String email;
    private List<Question> questions;
    private List<Answer> answers;
    private List<Comment> comments;

    public UserDto(SiteUser user){
        this.username=user.getUsername();
        this.questions=user.getQuestions();
        this.answers=user.getAnswers();
        this.comments=user.getComments();
        this.email=user.getEmail();
    }
}
