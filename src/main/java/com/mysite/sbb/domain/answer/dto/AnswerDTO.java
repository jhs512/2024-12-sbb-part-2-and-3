package com.mysite.sbb.domain.answer.dto;

import com.mysite.sbb.domain.user.dto.SiteUserDTO;
import com.mysite.sbb.domain.user.entity.SiteUser;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
public record AnswerDTO(
        Long id,
        String content,
        SiteUserDTO author,
        Long questionId,
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
