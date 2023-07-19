package com.templateproject.api.repository;

import com.templateproject.api.entity.RecipeCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeCartRepository extends JpaRepository<RecipeCart,Long> {
}
