package com.templateproject.api.controller;

import com.templateproject.api.entity.IngredientShoppingList;
import com.templateproject.api.entity.User;
import com.templateproject.api.repository.IngredientShoppingListRepository;
import com.templateproject.api.repository.ShoppingListRepository;
import com.templateproject.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/shoppinglist")
public class ShoppingListController {

    public final ShoppingListRepository shoppingListRepository;
    public final IngredientShoppingListRepository ingredientShoppingListRepository;
    public  final UserRepository userRepository;

    public ShoppingListController(ShoppingListRepository shoppingListRepository,
                                  IngredientShoppingListRepository ingredientShoppingListRepository,
                                  UserRepository userRepository) {
        this.shoppingListRepository = shoppingListRepository;
        this.ingredientShoppingListRepository = ingredientShoppingListRepository;
        this.userRepository = userRepository;
    }

    // créer la liste de course à partir des recettes du panier
    // et avant effacer toutes les lignes d'un utilisateur dans IngredientShoppingList
    @PostMapping("/{userId}")
    public boolean create(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            for (IngredientShoppingList ingredient :optionalUser.get().getShoppingList().getIngredientToShopList()) {
                System.out.println(ingredient.getId());
                this.ingredientShoppingListRepository.deleteById(ingredient.getId());
            };
            this.ingredientShoppingListRepository.insertIngredient(optionalUser.get().getId());
            return true;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId);
    }
    // effacer une ligne dans IngredientShoppingList
    // modifier la quantité sur une ligne dans IngredientShoppingList
    // ajouter un commentaire sur une ligne dans IngredientShoppingList
    // effacer toutes les lignes d'un utilisateur dans IngredientShoppingList
}
