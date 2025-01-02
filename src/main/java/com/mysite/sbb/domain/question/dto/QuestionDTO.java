package com.mysite.sbb.domain.question.dto;

import com.mysite.sbb.domain.answer.dto.AnswerDTO;
import com.mysite.sbb.domain.user.dto.SiteUserDTO;
import com.mysite.sbb.domain.user.entity.SiteUser;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Builder
public record QuestionDTO(
        Long id,
        String subject,
        String content,
        SiteUserDTO author,
        List<AnswerDTO> answerList,
        Set<SiteUser> voter,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {
    public LocalDateTime getCreateDate() {
        return createdAt;
    }

    public LocalDateTime getModifyDate() {
        return modifiedAt;
    }

}
