package com.templateproject.api.repository;

import com.templateproject.api.entity.Recipe;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(value = "SELECT new com.templateproject.api.entity.Recipe(r.id, r.title, " +
            "r.difficulty, r.budget, r.prepTime, r.cookTime, r.imageLink) " +
            "FROM Recipe r ")
    List<Recipe> findRecipeSummary();

    List<Recipe> findByTitleContaining(String title);

    @Query(value = "SELECT new com.templateproject.api.entity.Recipe(r.id, r.title, " +
            "r.difficulty, r.budget, r.prepTime, r.cookTime, r.imageLink) " +
            "FROM Recipe r "+
            "JOIN r.recipeCategories rc " +
            "WHERE rc.name LIKE CONCAT('%',:name,'%') "
    )
    List<Recipe> findSearchRecipeCategories(String name);

    @Query(value = "SELECT new com.templateproject.api.entity.Recipe(r.id, r.title, " +
            "r.difficulty, r.budget, r.prepTime, r.cookTime, r.imageLink) " +
            "FROM Recipe r "+
            "JOIN r.recipeCategories rc " +
            "WHERE rc.name LIKE CONCAT('%',:name,'%') OR r.title LIKE CONCAT('%',:name,'%') "
    )
    List<Recipe> findSearchRecipeCategoriesOrTitle(String name);
}
