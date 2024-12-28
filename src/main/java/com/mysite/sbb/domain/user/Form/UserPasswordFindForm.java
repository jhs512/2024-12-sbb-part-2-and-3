package com.mysite.sbb.domain.user.Form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordFindForm {
    @Size(min=3, max=25)
    @NotEmpty(message = "사용자 ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "EMAIL은 필수항목입니다.")
    @Email
    private String email;
}
