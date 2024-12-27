package com.ll.sbb.question;

import com.ll.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public List<Question> getList() {
        return questionRepository.findAll();
    }

    public Question getQuestion(long id) {
        Optional<Question> op = questionRepository.findById(id);
        if (op.isPresent()) {
            return op.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
}
