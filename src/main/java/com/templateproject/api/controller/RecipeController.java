package com.templateproject.api.controller;

import com.templateproject.api.entity.Recipe;
import com.templateproject.api.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@RestController
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/recipes")
    public List<Recipe> getAllRecipes(){
        return this.recipeRepository.findAll();
    }

    @GetMapping("/recipes/{id}")
    public Recipe getRecipe(@PathVariable Long id){
        return this.recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/recipes")
    public Recipe create(@RequestBody Recipe newRecipe){
        return this.recipeRepository.save(newRecipe);
    }

    @PutMapping("/recipes/{id}")
    public Recipe update(@PathVariable Long id, @RequestBody Recipe recipe){
        Recipe recipeToUpdate = recipeRepository.findById(id).get();
        return recipeRepository.save(recipeToUpdate);
    }
}
