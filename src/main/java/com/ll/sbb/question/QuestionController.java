package com.ll.sbb.question;

import com.ll.sbb.answer.AnswerForm;
import com.ll.sbb.user.SiteUser;
import com.ll.sbb.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model
            , @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Question> paging = questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") long id
            , Model model
            , AnswerForm answerForm) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public String questionCreate(@Valid QuestionForm questionForm
            , BindingResult bindingResult
            , Principal principal) {

        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        SiteUser user = userService.getUser(principal.getName());

        questionService.create(questionForm.getSubject(), questionForm.getContent(), user);

        return "redirect:/question/list";
    }

    @GetMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String questionModify(QuestionForm questionForm
            , @PathVariable("id") long id
            , Principal principal) {
        Question question = questionService.getQuestion(id);

        if (!question.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());

        return "question_form";
    }

    @PostMapping("/modify/{id}")
    @PreAuthorize("isAuthenticated()")
    public String questionModify(@Valid QuestionForm questionForm
            , BindingResult bindingResult
            , Principal principal
            , @PathVariable("id") long id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        Question question = questionService.getQuestion(id);

        if (!question.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionService.modify(question, questionForm.getSubject(), questionForm.getContent());

        return String.format("redirect:/question/detail/%s", id);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String questionDelete(Principal principal
            , @PathVariable("id") long id) {
        Question question = questionService.getQuestion(id);

        if (!question.getUser().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다");
        }

        questionService.delete(question);

        return "redirect:/";
    }


}
