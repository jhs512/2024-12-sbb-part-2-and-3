package com.ll.sbb.answer;

import com.ll.sbb.question.Question;
import com.ll.sbb.question.QuestionService;
import com.ll.sbb.user.SiteUser;
import com.ll.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
            model.addAttribute("question" , question);
            return "question_detail";
        }

        answerService.create(answerForm.getContent(), question, user);
        return "redirect:/question/detail/%d".formatted(id);
    }
}
