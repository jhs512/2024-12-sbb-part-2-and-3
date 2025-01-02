package com.mysite.sbb.domain.answer.form;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerForm {

        @NotEmpty(message = "내용은 필수항목입니다.")
        private String content;
}