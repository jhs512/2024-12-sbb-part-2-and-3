package com.mysite.sbb.global.security;

import lombok.Getter;

@Getter
public enum HTTPMethod {
    DELETE("삭제"), MODIFY("수정");

    private String meg;

    HTTPMethod(String msg) {
        this.meg = msg;
    }
}
