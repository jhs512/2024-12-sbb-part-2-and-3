package com.ann.annovation.answer;

import com.ann.annovation.question.Question;
import com.ann.annovation.question.QuestionService;
import com.ann.annovation.user.SiteUser;
import com.ann.annovation.user.UserService;
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

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(
            Model model,  // 뷰(View)에 데이터를 전달하기 위한 Model 객체
            @PathVariable("id") Integer id,  // URL의 {id} 값을 Integer 타입 변수 id에 매핑
            @Valid AnswerForm answerForm, BindingResult bindingResult,
            Principal principal // Spring Security의 객체로, 현재 로그인한 사용자의 정보를 알 수 있음
    ) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.answerService.create(question, answerForm.getContent(), siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }
}