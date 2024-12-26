package com.ann.annovation.category;

import com.ann.annovation.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void create(String name) {
        Category category = new Category();
        category.setName(name);
        this.categoryRepository.save(category);
    }

    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    public Category getCategoryByName(String name) {
        Optional<Category> oc = this.categoryRepository.findByName(name);
        if (oc.isPresent()) {
            return oc.get();
        } else {
            throw new DataNotFoundException("category not found");
        }
    }
}
