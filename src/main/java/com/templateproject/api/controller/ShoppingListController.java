package com.templateproject.api.controller;

import com.templateproject.api.entity.IngredientShoppingList;
import com.templateproject.api.entity.User;
import com.templateproject.api.repository.IngredientShoppingListRepository;
import com.templateproject.api.repository.ShoppingListRepository;
import com.templateproject.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/shoppinglists")
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

    @GetMapping("/{id}")
    public List<IngredientShoppingList> getByShoppingListId(@PathVariable Long id) {
        return this.shoppingListRepository.findById(id).get().getIngredientToShopList();
    }

    @PostMapping("/{userId}")
    public boolean create(@PathVariable Long userId) {

        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            this.ingredientShoppingListRepository.deleteAll(
                    optionalUser.get().getShoppingList().getIngredientToShopList());
            this.ingredientShoppingListRepository.insertIngredient(optionalUser.get().getId());
            return true;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId);
    }

    //TODO mettre à jour les valeurs des champs après modif dans IngredientShoppingList

    @DeleteMapping("/ingredients/{id}")
    public void delete(@PathVariable Long id) {
        Optional<IngredientShoppingList> optionalIngredient = ingredientShoppingListRepository.findById(id);
        if (optionalIngredient.isPresent()){
            this.shoppingListRepository.deleteById(id);
        }
    }
    @DeleteMapping("/{userId}")
    public void deleteAll(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            this.ingredientShoppingListRepository.deleteAll(
                    optionalUser.get().getShoppingList().getIngredientToShopList());
        }
    }
}
