package com.mysite.sbb.global.superclass;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
public class OnlyId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
}