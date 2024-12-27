package com.ll.sbb.answer;

import com.ll.sbb.question.Question;
import com.ll.sbb.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(@PathVariable("id") long id
            , Model model
            , @RequestParam(value = "content") String content) {

        Question question = questionService.getQuestion(id);
        answerService.create(content, question);
        return "redirect:/question/detail/%d".formatted(id);
    }
}
