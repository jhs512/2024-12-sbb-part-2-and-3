package com.mysite.sbb.domain.answer.controller;

import com.mysite.sbb.domain.answer.dto.AnswerDTO;
import com.mysite.sbb.domain.answer.form.AnswerForm;
import com.mysite.sbb.domain.answer.service.AnswerService;
import com.mysite.sbb.domain.question.mapper.QuestionMapper;
import com.mysite.sbb.domain.question.service.QuestionService;
import com.mysite.sbb.domain.user.entity.SiteUser;
import com.mysite.sbb.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;
    private final QuestionMapper questionMapper;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{questionId}")
    public String createAnswer(Model model, @PathVariable("questionId") Long questionId,
                               @Valid AnswerForm answerForm, BindingResult bindingResult,
                               Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", questionService.getQuestion(questionId));
            return "question_detail";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());

        AnswerDTO answer = answerService.create(questionId, answerForm.getContent(), siteUser);

        return String.format("redirect:/question/detail/%s#answer_%s", questionId, answer.id());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{answerId}")
    public String answerModify(AnswerForm answerForm, @PathVariable("answerId") Long answerId, Principal principal) {
        AnswerDTO answer = this.answerService.getAnswer(answerId);
        if (!answer.author().username().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerForm.setContent(answer.content());
        return "answer_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{answerId}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult,
                               @PathVariable("answerId") Long answerId, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        AnswerDTO answer = this.answerService.getAnswer(answerId);
        if (!answer.author().username().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        answerService.modify(answer.id(), answerForm.getContent());
        return String.format("redirect:/question/detail/%s#answer_%s",
                answer.questionId(), answerId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{answerId}")
    public String answerDelete(Principal principal, @PathVariable("answerId") Long answerId) {
        AnswerDTO answer = answerService.getAnswer(answerId);
        if (!answer.author().username().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.answerService.delete(answer.id());
        return String.format("redirect:/question/detail/%s", answer.questionId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{answerId}")
    public String answerVote(Principal principal, @PathVariable("answerId") Long answerId) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.answerService.vote(answerId, siteUser);
        return String.format("redirect:/question/detail/%s#answer_%s", answerService.getQuestionIdByAnswerId(answerId),answerId);
    }
}
