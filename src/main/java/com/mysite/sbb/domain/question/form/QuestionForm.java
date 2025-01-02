package com.mysite.sbb.domain.question.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionForm {

        @NotEmpty(message = "제목은 필수항목입니다.")
        @Size(max = 200)
        private String subject;

        @NotEmpty(message = "내용은 필수항목입니다.")
        private String content;
}
