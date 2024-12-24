package com.ll.sbb.category;


import com.ll.sbb.DataNotFoundException;
import com.ll.sbb.question.Question;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category getCategory(String name) {
        Optional<Category> category = this.categoryRepository.findByName(name);
        if (category.isPresent()) {
            return category.get();
        } else {
            throw new DataNotFoundException("category not found");
        }
    }

    public void createCategory(Category category) {
        this.categoryRepository.save(category);
    }
}
