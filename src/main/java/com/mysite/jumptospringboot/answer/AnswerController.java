package com.mysite.jumptospringboot.answer;

import com.mysite.jumptospringboot.question.Question;
import com.mysite.jumptospringboot.question.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;

    @PostMapping("/create/{id}")
    public String create(Model model, @PathVariable("id") Integer id, @RequestParam(value="content") String content) {
        System.out.println("content : " + content);
        System.out.println("id : " + id);
        Question question = questionService.getQuestion(id);
        answerService.create(question,content);
        return String.format("redirect:/question/detail/%s", id);
    }
}
