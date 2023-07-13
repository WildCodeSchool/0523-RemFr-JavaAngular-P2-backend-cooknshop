package com.templateproject.api.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;


import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")

    private Long id;
    private String name;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "recipeCategories")
    Set<Recipe> recipesByCategory = new HashSet<>();

    public Category() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Recipe> getRecipesByCategory() {
        return recipesByCategory;
    }

    public void setRecipesByCategory(Set<Recipe> recipesByCategory) {
        this.recipesByCategory = recipesByCategory;
    }
}
