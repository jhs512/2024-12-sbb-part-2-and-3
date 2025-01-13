package com.ll.__01_13_sbb.Answer;

import com.ll.__01_13_sbb.Question.Question;
import com.ll.__01_13_sbb.Question.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable Integer id) {
        Question question = this.questionService.getQuestion(id);
        if(bindingResult.hasErrors()){
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.answerService.create(answerForm.getContent(), question);
        return String.format("redirect:/question/detail/%s", id);
    }
}
