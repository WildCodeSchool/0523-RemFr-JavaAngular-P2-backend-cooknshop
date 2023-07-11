package com.templateproject.api.entity;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imageLink;

    @OneToMany(mappedBy = "shoppingListIngredient")
    private List<IngredientShoppingList> ingredientToShopList = new ArrayList<>();

    @OneToMany(mappedBy = "ingredient")
    private List<RecipeIngredient> ingredientList = new ArrayList<>();

    public Ingredient(){

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

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<IngredientShoppingList> getIngredientToShopList() {
        return ingredientToShopList;
    }

    public void setIngredientToShopList(List<IngredientShoppingList> ingredientToShopList) {
        this.ingredientToShopList = ingredientToShopList;
    }

    public List<RecipeIngredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<RecipeIngredient> ingredientList) {
        this.ingredientList = ingredientList;
    }
}
