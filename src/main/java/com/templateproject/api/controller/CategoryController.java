package com.templateproject.api.controller;

import com.templateproject.api.entity.Category;
import com.templateproject.api.repository.CategoryRepository;
import com.templateproject.api.utils.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    public final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("")
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }

    @GetMapping("{id}")
    public Category getById(@PathVariable Long id){
        return this.categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    public Category create(@RequestBody Category newCategory) {
        return this.categoryRepository.save(newCategory);
    }

    @PutMapping("/{id}")
    public Category update(@PathVariable Long id, @RequestBody Category categoryUpdate) {
        Category category = this.categoryRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        BeanUtils.copyNonNullProperties(categoryUpdate, category);

        return this.categoryRepository.save(category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            this.categoryRepository.deleteById(id);
        }
    }
}
