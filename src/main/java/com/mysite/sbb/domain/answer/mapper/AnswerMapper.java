package com.mysite.sbb.domain.answer.mapper;

import com.mysite.sbb.domain.answer.dto.AnswerDTO;
import com.mysite.sbb.domain.answer.entity.Answer;
import com.mysite.sbb.domain.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnswerMapper {
    private final UserMapper userMapper;

    // Entity → DTO 변환
    public AnswerDTO toDTO(Answer answer) {
        return AnswerDTO.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .author(userMapper.toDTO(answer.getAuthor()))
                .questionId(answer.getQuestion().getId())
                .voter(answer.getVoter())
                .createdAt(answer.getCreateDate())
                .modifiedAt(answer.getModifyDate())
                .build();
    }
}
