package com.templateproject.api.controller;

import com.templateproject.api.entity.Category;
import com.templateproject.api.entity.Recipe;
import com.templateproject.api.repository.CategoryRepository;
import com.templateproject.api.repository.RecipeCategoryRepository;
import com.templateproject.api.repository.RecipeRepository;
import com.templateproject.api.utils.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final RecipeCategoryRepository recipeCategoryRepository;

    public RecipeController(
            RecipeRepository recipeRepository,
            CategoryRepository categoryRepository,
            RecipeCategoryRepository recipeCategoryRepository
    ) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.recipeCategoryRepository = recipeCategoryRepository;
    }

    @GetMapping("")
    public List<Recipe> getAllRecipes(){
        return this.recipeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable Long id){
        return this.recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    public Recipe create(@RequestBody Recipe newRecipe){
        return this.recipeRepository.save(newRecipe);
    }

    @PostMapping("/{recipeId}/categories/{categoryId}/recipecategories")
    public Recipe addCategoryToRecipe(
            @PathVariable Long recipeId,
            @PathVariable Long categoryId
    ) {
        Recipe recipe = this.recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Recipe not found with id" + recipeId));

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found with id" + categoryId));

        Set<Category> categories = recipe.getRecipeCategories();
        categories.add(category);

        return this.recipeRepository.save(recipe);
    }

    @PutMapping("/{id}")
    public Recipe update(@PathVariable Long id, @RequestBody Recipe recipeUpdate) {
        Recipe recipe = this.recipeRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        BeanUtils.copyNonNullProperties(recipeUpdate, recipe);

        return this.recipeRepository.save(recipe);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            this.recipeRepository.deleteById(id);
        }
    }
}
