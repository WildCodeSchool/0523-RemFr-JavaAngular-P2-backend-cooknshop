package com.templateproject.api.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Recipe {

    public enum Budget {
        CHEAP,
        MODERATE,
        EXPENSIVE
    }

    public enum Difficulty {
        EASY,
        MODERATE,
        HARD
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    private Budget budget;
    private Short prepTime;
    private Short cookTime;
    private String imageLink;

    @ManyToMany
    @JoinTable(
            name = "recipe_categories",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categoryRecipes;

    @OneToMany(mappedBy = "recipe")
    private List<Step> stepList = new ArrayList<>();

    public Recipe() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Short getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Short prepTime) {
        this.prepTime = prepTime;
    }

    public Short getCookTime() {
        return cookTime;
    }

    public void setCookTime(Short cookTime) {
        this.cookTime = cookTime;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Set<Category> getCategoryRecipes() {
        return categoryRecipes;
    }

    public void setCategoryRecipes(Set<Category> categoryRecipes) {
        this.categoryRecipes = categoryRecipes;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }
}
