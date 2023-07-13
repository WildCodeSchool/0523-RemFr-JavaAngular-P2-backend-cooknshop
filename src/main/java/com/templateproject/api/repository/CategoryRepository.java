package com.templateproject.api.repository;

import com.templateproject.api.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findCategoryByName(String name);

    List<Category> findCategoriesByNameContaining(String name);

    List<Category> findByNameContaining(String name);
}