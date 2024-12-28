package com.ll.sbb.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateForm {

    @NotEmpty(message = "사용자 ID를 입력해주세요")
    @Size(min = 2, max = 25)
    private String username;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password1;

    @NotEmpty(message = "비밀번호 확인을 입력해주세요")
    private String password2;

    @NotEmpty(message = "이메일을 입력해주세요")
    @Email
    private String email;
}
