package com.templateproject.api.entity;

import jakarta.persistence.*;


import java.util.List;

@Entity
public class Recipe {

    public enum Budget {
        CHEAP,
        MODERATE,
        EXPENSIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    public enum Difficulty {
        EASY,
        MODERATE,
        HARD
    }

    @Enumerated(EnumType.STRING)
    private Budget budget;
    private Short prepTime;
    private Short cookTime;
    private String imageLink;

    public Recipe(){

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
}
