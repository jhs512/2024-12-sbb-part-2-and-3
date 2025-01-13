package com.ll.__01_13_sbb.Answer;

import com.ll.__01_13_sbb.Question.Question;
import com.ll.__01_13_sbb.Question.QuestionService;
import com.ll.__01_13_sbb.User.SiteUser;
import com.ll.__01_13_sbb.User.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable Integer id, Principal principal) {
        Question question = this.questionService.getQuestion(id);
        SiteUser user = this.userService.getUser(principal.getName());
        if(bindingResult.hasErrors()){
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.answerService.create(answerForm.getContent(), question, user);
        return String.format("redirect:/question/detail/%s", id);
    }
}
