package com.templateproject.api.repository;

import com.templateproject.api.entity.Recipe;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(value = "SELECT new com.templateproject.api.entity.Recipe(r.id, r.title, " +
            "r.difficulty, r.budget, r.prepTime, r.cookTime, r.imageLink) " +
            "FROM Recipe r ")
    List<Recipe> findRecipeSummary();
}
