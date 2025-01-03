package com.ann.annovation.comment;

import java.security.Principal;

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

import com.ann.annovation.answer.Answer;
import com.ann.annovation.answer.AnswerService;
import com.ann.annovation.question.Question;
import com.ann.annovation.question.QuestionService;
import com.ann.annovation.user.SiteUser;
import com.ann.annovation.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comment")
@Controller
public class CommentController {

    private final CommentService commentService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create/question/{id}")
    public String questionCommentCreate(
            Model model,
            @PathVariable("id") Integer id,
            @Valid CommentForm commentForm,
            BindingResult bindingResult,
            Principal principal) {

        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        this.commentService.create(commentForm.getContent(), question, null, siteUser);
        return String.format("redirect:/question/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/create/answer/{id}")
    public String answerCommentCreate(
            Model model,
            @PathVariable("id") Integer id,
            @Valid CommentForm commentForm,
            BindingResult bindingResult,
            Principal principal) {

        Answer answer = this.answerService.getAnswer(id);
        Question question = answer.getQuestion();
        SiteUser siteUser = this.userService.getUser(principal.getName());

        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            model.addAttribute("commentForm", commentForm);
            return "question_detail";
        }
        this.commentService.create(commentForm.getContent(), question, answer, siteUser);
        return String.format("redirect:/question/detail/%s#answer_%s", question.getId(), answer.getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/delete/{id}")
    public String commentDelete(
            @PathVariable("id") Integer id,
            Principal principal) {
        Comment comment = this.commentService.getComment(id);
        if (!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }

        Answer answer = comment.getAnswer();
        Question question = comment.getQuestion();
        this.commentService.delete(comment);

        // 답변 댓글인 경우 해당 답변 위치로 이동
        if (answer != null) {
            return String.format("redirect:/question/detail/%s#answer_%s",
                    question.getId(), answer.getId());
        }
        // 질문 댓글인 경우 질문 상단으로 이동
        return String.format("redirect:/question/detail/%s", question.getId());


    }
}
