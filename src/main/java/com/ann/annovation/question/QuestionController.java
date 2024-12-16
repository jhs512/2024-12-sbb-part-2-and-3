package com.ann.annovation.question;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public  String list(Model model) {

        // QuestionService의 getList 메서드를 호출하여 모든 Question 객체를 가져옴
        List<Question> questionList = this.questionService.getList();

        // 가져온 questionList를 Model 객체에 추가하여 뷰에서 사용할 수 있도록 전달
        // 뷰(View) : 사용자가 브라우저에서 보는 모든 것(버튼, 텍스트, 이미지 등)
        model.addAttribute("questionList", questionList);

        // 반환 값으로 "question_list"라는 이름의 뷰를 렌더링하도록 지시
        // 렌더링(Rendering)은 데이터를 사용자가 볼 수 있는 화면으로 변환하는 과정
        return "question_list";
    }
    @GetMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        // QuestionService의 getQuestion 메서드를 호출하여 특정 id에 해당하는 질문 데이터를 가져옴
        Question question = this.questionService.getQuestion(id);

        // 가져온 질문 데이터를 모델(Model)에 추가
        // "question"이라는 이름으로 뷰에 전달하여 화면에서 사용 가능
        // model.addAttribute("key", value)를 통해 데이터를 추가하며, 뷰에서는 key 이름으로 접근합니다.
        model.addAttribute("question", question);
        return "question_detail";
    }
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list"; // 질문 저장후 질문목록으로 이동
    }
}