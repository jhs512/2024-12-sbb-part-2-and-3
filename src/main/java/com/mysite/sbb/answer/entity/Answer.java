package com.mysite.sbb.answer.entity;

import com.mysite.sbb.global.entity.PostBaseEntity;
import com.mysite.sbb.question.entity.Question;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Answer extends PostBaseEntity {
    // PostBaseEntity : id, content, createdDate, author, modifyDate, voter
    @ManyToOne
    private Question question;
}
