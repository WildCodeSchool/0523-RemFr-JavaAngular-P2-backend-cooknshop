package com.templateproject.api.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
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
    @Column(name = "recipe_id")

    private Long id;
    private String title;
    @Enumerated(EnumType.STRING)
    private Difficulty difficulty;

    @Enumerated(EnumType.STRING)
    private Budget budget;
    private Float prepTime;
    private Float cookTime;
    private String imageLink;

    private Integer nbPerson;

    @ManyToMany
    @JoinTable(
            name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> recipeCategories;

    @OneToMany(mappedBy = "recipe")
    private Set<Comment> recipeComment = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private Set<RecipeCart> recipeCart = new HashSet<>();

    @OneToMany(mappedBy = "recipe")
    private Set<RecipeIngredient> recipeIngredient = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Step> stepList = new ArrayList<>();

    @ManyToMany(mappedBy = "favoriteRecipes")
    private Set<User> usersWhoLikeRecipe  = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Recipe() {
    }

    public Recipe(Long id, String title, Difficulty difficulty, Budget budget, Float prepTime,
                  Float cookTime, String imageLink) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.budget = budget;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.imageLink = imageLink;
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

    public Float getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Float prepTime) {
        this.prepTime = prepTime;
    }

    public Float getCookTime() {
        return cookTime;
    }

    public void setCookTime(Float cookTime) {
        this.cookTime = cookTime;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public Integer getNbPerson() {
        return nbPerson;
    }

    public void setNbPerson(Integer nbPerson) {
        this.nbPerson = nbPerson;
    }

    public Set<Category> getRecipeCategories() {
        return recipeCategories;
    }

    public void setRecipeCategories(Set<Category> recipeCategories) {
        this.recipeCategories = recipeCategories;
    }

    public Set<Comment> getRecipeComment() {
        return recipeComment;
    }

    public void setRecipeComment(Set<Comment> recipeComment) {
        this.recipeComment = recipeComment;
    }

    public Set<RecipeCart> getRecipeCart() {
        return recipeCart;
    }

    public void setRecipeCart(Set<RecipeCart> recipeCart) {
        this.recipeCart = recipeCart;
    }

    public Set<RecipeIngredient> getRecipeIngredient() {
        return recipeIngredient;
    }

    public void setRecipeIngredient(Set<RecipeIngredient> recipeIngredient) {
        this.recipeIngredient = recipeIngredient;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    public Set<User> getUsersWhoLikeRecipe() {
        return usersWhoLikeRecipe;
    }

    public void setUsersWhoLikeRecipe(Set<User> usersWhoLikeRecipe) {
        this.usersWhoLikeRecipe = usersWhoLikeRecipe;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
