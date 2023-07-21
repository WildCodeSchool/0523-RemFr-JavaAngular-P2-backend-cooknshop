package com.templateproject.api;
/*
import java.util.*;

import com.github.javafaker.Faker;
import com.templateproject.api.entity.Category;
import com.templateproject.api.entity.Ingredient;
import com.templateproject.api.entity.Recipe;
import com.templateproject.api.entity.RecipeIngredient;
import com.templateproject.api.repository.CategoryRepository;
import com.templateproject.api.repository.IngredientRepository;
import com.templateproject.api.repository.RecipeIngredientRepository;
import com.templateproject.api.repository.RecipeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final Faker faker;
    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;
    public DatabaseSeeder(
            RecipeRepository recipeRepository,
            CategoryRepository categoryRepository,
            IngredientRepository ingredientRepository,
            RecipeIngredientRepository recipeIngredientRepository
    ){
        this.faker = new Faker();
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Categories
        List<Category> allCategories = new ArrayList<>();
        Category soup = new Category();
        soup.setName("Soup");
        allCategories.add(soup);

        Category snack = new Category();
        snack.setName("Snack");
        allCategories.add(snack);

        Category mainCourse = new Category();
        mainCourse.setName("Main Course");
        allCategories.add(mainCourse);

        Category salad = new Category();
        salad.setName("Salad");
        allCategories.add(salad);

        Category stew = new Category();
        stew.setName("Stew");
        allCategories.add(stew);

        Random randCategories = new Random();
        int ubCategories = allCategories.size() - 1;

        categoryRepository.saveAll(allCategories);

        //Ingredients
        List<Ingredient> allIngredients = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Ingredient myingredient = new Ingredient();
            myingredient.setName(faker.food().ingredient());
            allIngredients.add(myingredient);
        }
        ingredientRepository.saveAll(allIngredients);
        Random randIngredient = new Random();
        int ubIngredient = allCategories.size() - 1;

        //Recipes
        for (int i = 0; i < 10; i++) {
            Recipe myRecipe = new Recipe();
            myRecipe.setTitle(faker.food().dish());

            Long longRandomCategories = randCategories.nextLong(ubCategories) + 1;
            Long longRandomIngredients = randCategories.nextLong(ubIngredient) + 1;

            Set myIngredient = new HashSet();
            myIngredient.add(ingredientRepository.getReferenceById(longRandomIngredients));
            myRecipe.setRecipeIngredient(myIngredient);


            Set myCategory = new HashSet();
            myCategory.add(categoryRepository.getReferenceById(longRandomCategories));
            myRecipe.setRecipeCategories(myCategory);

            recipeRepository.save(myRecipe);

            //RecipeIngredient
            RecipeIngredient myRecipeIngredient = new RecipeIngredient();
            myRecipeIngredient.setIngredient(ingredientRepository.getReferenceById(longRandomIngredients));
            myRecipeIngredient.setRecipe(recipeRepository.getReferenceById(myRecipe.getId()));
            recipeIngredientRepository.save(myRecipeIngredient);

        }
    }

}
*/