package com.mysite.sbb.answer.controller;

import com.mysite.sbb.answer.entity.Answer;
import com.mysite.sbb.answer.entity.AnswerForm;
import com.mysite.sbb.answer.service.AnswerService;
import com.mysite.sbb.global.security.Check;
import com.mysite.sbb.global.security.HTTPMethod;
import com.mysite.sbb.question.entity.Question;
import com.mysite.sbb.question.service.QuestionService;
import com.mysite.sbb.user.entity.SiteUser;
import com.mysite.sbb.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer questionId,
                               @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        Question question = this.questionService.getQuestion(questionId);
        if(bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        SiteUser user = this.userService.getUser(principal.getName());
        this.answerService.createAnswer(question, answerForm.getContent(), user);
        return "redirect:/question/detail/%s".formatted(questionId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyAnswer(AnswerForm answerForm, @PathVariable("id") Integer answerId, Principal principal) {
        Answer answer = this.answerService.getAnswer(answerId);
        Check.permission(answer, HTTPMethod.MODIFY, principal);
        answerForm.setContent(answer.getContent());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyAnswer(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               Principal principal, @PathVariable("id") Integer answerId) {
        Answer answer = this.answerService.getAnswer(answerId);
        Check.permission(answer, HTTPMethod.MODIFY, principal);
        this.answerService.modifyAnswer(answer, answerForm.getContent());
        return "redirect:/question/detail/%s".formatted(answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteAnswer(Principal principal, @PathVariable("id") Integer answerId) {
        Answer answer = this.answerService.getAnswer(answerId);
        Check.permission(answer, HTTPMethod.DELETE, principal);
        this.answerService.deleteAnswer(answer);
        return "redirect:/question/detail/%s".formatted(answer.getQuestion().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String voteAnswer(Principal principal, @PathVariable("id") Integer answerId) {
        Answer answer = this.answerService.getAnswer(answerId);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.answerService.voteAnswer(answer, siteUser);
        return "redirect:/question/detail/%s".formatted(answer.getQuestion().getId());
    }

}
