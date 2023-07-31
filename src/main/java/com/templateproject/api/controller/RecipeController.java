package com.templateproject.api.controller;

import com.templateproject.api.entity.*;
import com.templateproject.api.repository.*;
import com.templateproject.api.utils.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final StepRepository stepRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final UserRepository userRepository;
    public RecipeController(
            RecipeRepository recipeRepository,
            CategoryRepository categoryRepository,
            StepRepository stepRepository,
            RecipeIngredientRepository recipeIngredientRepository,
            UserRepository userRepository
    ) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.stepRepository = stepRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("")
    public List<Recipe> getAll() {
        return this.recipeRepository.findAll();
    }

    @GetMapping("/title/containing/{query}")
    public List<Recipe>  queryRecipeFromTitle(@PathVariable String query) {
        return this.recipeRepository.findByTitleContaining(query);

    }
    @GetMapping("/category/containing/{query}")
    public List<Recipe> queryRecipeFromCategory(@PathVariable String query) {
        return this.recipeRepository.findSearchRecipeCategories(query);
    }


    @GetMapping("/both/containing/{query}")
    public List<Recipe> queryRecipeFromBoth(@PathVariable String query) {
        return this.recipeRepository.findSearchRecipeCategoriesOrTitle(query);
    }

    @GetMapping("/summary")
    public List<Recipe> getAllRecipes() {
        return this.recipeRepository.findRecipeSummary();
    }

    @GetMapping("/{id}")
    public Recipe getRecipe(@PathVariable Long id) {
        return this.recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}/steps")
    public List<Step> getStep(@PathVariable Long id) {
        Recipe recipe = this.recipeRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return recipe.getStepList();
    }

    @GetMapping("/{id}/ingredients")
    public Set<RecipeIngredient> getIngredient(@PathVariable Long id) {
        Recipe recipe = this.recipeRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return recipe.getRecipeIngredient();
    }

    @PostMapping("")
    public Recipe create(@RequestBody Recipe newRecipe) {

        Recipe savedRecipe = this.recipeRepository.save(newRecipe);

        for (RecipeIngredient newIngredient : newRecipe.getRecipeIngredient()) {
            newIngredient.setRecipe(savedRecipe);
            this.recipeIngredientRepository.save(newIngredient);
        }

        for (int i = 0; i < newRecipe.getStepList().size(); i++) {
            Step newStep = newRecipe.getStepList().get(i);
            newStep.setRecipe(savedRecipe);
            newStep.setNumber((short) (i + 1));
            this.stepRepository.save(newStep);
        }

        return savedRecipe;
    }

    @PostMapping("/{recipeId}/steps")
    public Recipe addStep(@PathVariable Long recipeId, @RequestBody List<Step> newStepList) {

        Recipe recipe = this.recipeRepository
                .findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Recipe not found with id " + recipeId));

        int i = recipe.getStepList().size() + 1;
        for (Step newStep : newStepList) {
            newStep.setRecipe(recipe);
            newStep.setNumber((short) i);
            this.stepRepository.save(newStep);
            i++;
        }

        return recipe;
    }

    @PostMapping("/{recipeId}/ingredients")
    public Recipe addIngredient(@PathVariable Long recipeId, @RequestBody List<RecipeIngredient> newIngredientList) {

        Recipe recipe = this.recipeRepository
                .findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Recipe not found with id " + recipeId));

        for (RecipeIngredient newIngredient : newIngredientList) {
            newIngredient.setRecipe(recipe);
            this.recipeIngredientRepository.save(newIngredient);
        }

        return recipe;
    }
    @GetMapping("{recipeId}/user/{userId}")
    public Set<User> addRecipeToFavorites(
            @PathVariable Long recipeId,
            @PathVariable Long userId
    ) {
        Recipe recipe = this.recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Recipe not found with id " + recipeId));
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id " + userId));
        
        Set<Recipe> favoriteRecipes = user.getFavoriteRecipes();
        favoriteRecipes.add(recipe);
        user.setFavoriteRecipes(favoriteRecipes);
        this.userRepository.save(user);
        return recipe.getUsersWhoLikeRecipe();
    }

    @PostMapping("/{recipeId}/categories/{categoryId}/recipecategories")
    public Recipe addCategoryToRecipe(
            @PathVariable Long recipeId,
            @PathVariable Long categoryId
    ) {
        Recipe recipe = this.recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Recipe not found with id " + recipeId));

        Category category = this.categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Category not found with id " + categoryId));

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
    public void delete(@PathVariable Long id) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isPresent()) {
            this.recipeRepository.deleteById(id);
        }
    }
}
