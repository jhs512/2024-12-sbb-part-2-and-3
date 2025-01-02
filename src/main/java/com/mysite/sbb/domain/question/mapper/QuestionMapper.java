package com.mysite.sbb.domain.question.mapper;

import com.mysite.sbb.domain.answer.mapper.AnswerMapper;
import com.mysite.sbb.domain.question.dto.QuestionDTO;
import com.mysite.sbb.domain.question.entity.Question;
import com.mysite.sbb.domain.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionMapper {
    private final UserMapper userMapper;
    private final AnswerMapper answerMapper;

    // Entity → DTO 변환
    public QuestionDTO toDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .subject(question.getSubject())
                .content(question.getContent())
                .author(userMapper.toDTO(question.getAuthor()))
                .voter(question.getVoter())
                .createdAt(question.getCreateDate())
                .modifiedAt(question.getModifyDate())
                .answerList(
                        question.getAnswerList().stream()
                                .map(answerMapper::toDTO)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
