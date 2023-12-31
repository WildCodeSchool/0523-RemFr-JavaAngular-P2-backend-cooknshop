package com.templateproject.api.repository;

import com.templateproject.api.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeCategoryRepository extends JpaRepository<Recipe, Long> {
}
