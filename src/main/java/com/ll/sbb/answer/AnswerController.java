package com.ll.sbb.answer;

import com.ll.sbb.question.Question;
import com.ll.sbb.question.QuestionService;
import com.ll.sbb.user.SiteUser;
import com.ll.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;
    private final UserService userService;

    @PostMapping("/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String createAnswer(@PathVariable("id") long id
            , Model model
            , @Valid AnswerForm answerForm
            , BindingResult bindingResult
            , Principal principal) {

        Question question = questionService.getQuestion(id);
        SiteUser user = userService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        Answer answer = answerService.create(answerForm.getContent(), question, user);

        return "redirect:/question/detail/%s#answer_%s".formatted(answer.getQuestion().getId(), answer.getId());
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modifyAnswer(
            @PathVariable("id") long id
            , AnswerForm answerForm
            , Principal principal) {
        Answer answer = answerService.getAnswer(id);

        if (!answer.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }

        answerForm.setContent(answer.getContent());

        return "answer_form";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String modifyAnswer(
            @PathVariable("id") long id
            , @Valid AnswerForm answerForm
            , BindingResult bindingResult
            , Principal principal) {

        if (bindingResult.hasErrors()) {
            return "answer_form";
        }

        Answer answer = answerService.getAnswer(id);

        if (!answer.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다");
        }

        answerService.modify(answer, answerForm.getContent());

        return "redirect:/question/detail/%s".formatted(answer.getQuestion().getId());
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deleteAnswer(
            @PathVariable("id") long id
            , Principal principal) {
        Answer answer = answerService.getAnswer(id);

        if (!answer.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
        }

        answerService.delete(answer);

        return "redirect:/question/detail/%s".formatted(answer.getQuestion().getId());
    }

    @GetMapping("vote/{id}")
    @PreAuthorize("isAuthenticated()")
    public String answerVote(Principal principal
            , @PathVariable("id") long id) {
        Answer answer = answerService.getAnswer(id);
        SiteUser user = userService.getUser(principal.getName());

        answerService.vote(answer, user);

        return "redirect:/question/detail/%s#answer_%s".formatted(answer.getQuestion().getId(), answer.getId());
    }
}
