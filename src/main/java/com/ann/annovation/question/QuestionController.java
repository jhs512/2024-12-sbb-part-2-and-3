package com.ann.annovation.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionRepository questionRepository;

    @GetMapping("/question/list")
    public  String list(Model model) {

        // QuestionRepository의 findAll 메서드를 호출하여 모든 Question 객체를 가져옴
        List<Question> questionList = this.questionRepository.findAll();

        // 가져온 questionList를 Model 객체에 추가하여 뷰에서 사용할 수 있도록 전달
        // 뷰(View) : 사용자가 브라우저에서 보는 모든 것(버튼, 텍스트, 이미지 등)
        model.addAttribute("questionList", questionList);

        // 반환 값으로 "question_list"라는 이름의 뷰를 렌더링하도록 지시
        // 렌더링(Rendering)은 데이터를 사용자가 볼 수 있는 화면으로 변환하는 과정
        return "question_list";
    }

}
