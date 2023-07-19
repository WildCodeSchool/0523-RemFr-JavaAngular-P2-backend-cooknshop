package com.templateproject.api.controller;

import com.templateproject.api.entity.Recipe;
import com.templateproject.api.entity.Step;
import com.templateproject.api.repository.RecipeRepository;
import com.templateproject.api.repository.StepRepository;
import com.templateproject.api.utils.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/steps")
public class StepController {

    private final StepRepository stepRepository;
    private final RecipeRepository recipeRepository;

    public StepController(StepRepository stepRepository, RecipeController recipeController, RecipeRepository recipeRepository) {
        this.stepRepository = stepRepository;
        this.recipeRepository = recipeRepository;
    }

    @GetMapping("")
    public List<Step> getAll() {
        return this.stepRepository.findAll();
    }

    @GetMapping("/{id}")
    public Step getById(@PathVariable Long id) {
        return this.stepRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    public Step create(@RequestBody Step newStep) {
        return this.stepRepository.save(newStep);
    }

    @PutMapping("/{id}")
    public Step update(@PathVariable Long id, @RequestBody Step newStep) {
        Step step = this.stepRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        BeanUtils.copyNonNullProperties(newStep, step);

        return this.stepRepository.save(step);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        Optional<Step> optionalStep = this.stepRepository.findById(id);
        if (optionalStep.isPresent()) {
            this.stepRepository.deleteById(id);
        }
    }

}
