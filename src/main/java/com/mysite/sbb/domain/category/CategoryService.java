package com.mysite.sbb.domain.category;

import com.mysite.sbb.global.exception.DataNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public void make(String name){
        Category category = Category.builder()
                .name(name)
                .build();
        categoryRepository.save(category);
    }

    public long count() {
        return categoryRepository.count();
    }

    public Category findById(Integer categoryId) {
        if(categoryRepository.findById(categoryId).isPresent()){
            return categoryRepository.findById(categoryId).get();
        }else{
            throw new DataNotFoundException("존재하지 않는 게시판");
        }
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
