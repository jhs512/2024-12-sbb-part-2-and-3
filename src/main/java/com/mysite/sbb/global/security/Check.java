package com.mysite.sbb.global.security;

import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.user.entity.SiteUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

public class Check {
    public static void permission(Object object, HTTPMethod httpMethod, Principal principal) {
        SiteUser author;
        if(object instanceof Question) {
            Question question = (Question) object;
            author = question.getAuthor();
        } else if (object instanceof Answer) {
            Answer answer = (Answer) object;
            author = answer.getAuthor();
        } else {
            throw new IllegalArgumentException("지원하지 않는 객체입니다.");
        }

        if(!author.getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
    }
}
