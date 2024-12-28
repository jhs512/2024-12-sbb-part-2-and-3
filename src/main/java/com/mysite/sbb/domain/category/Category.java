package com.mysite.sbb.domain.category;

import com.mysite.sbb.global.superclass.OnlyId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends OnlyId {
    @Column(length=30)
    private String name;

    @Builder
    public Category(String name){
        this.name=name;
    }
}
