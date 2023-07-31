package com.templateproject.api.controller;

import com.templateproject.api.entity.IngredientShoppingList;
import com.templateproject.api.entity.ShoppingList;
import com.templateproject.api.entity.Unit;
import com.templateproject.api.entity.User;
import com.templateproject.api.repository.IngredientShoppingListRepository;
import com.templateproject.api.repository.ShoppingListRepository;
import com.templateproject.api.repository.UserRepository;
import com.templateproject.api.utils.BeanUtils;
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

    @GetMapping("/{userId}")
    public List<IngredientShoppingList> getByShoppingListId(@PathVariable Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            return this.shoppingListRepository
                    .findById(optionalUser.get().getShoppingList().getId())
                    .get().getIngredientToShopList();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId);
    }

    @GetMapping("/{shoppingListId}/ingredients")
    public List<IngredientShoppingList> getIngredientShoppingListById(@PathVariable Long shoppingListId) {
        Optional<ShoppingList> optionalShoppingList = shoppingListRepository.findById(shoppingListId);
        if (optionalShoppingList.isPresent()){
            return optionalShoppingList.get().getIngredientToShopList();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ShoppingList not found with id " + shoppingListId);
    }

    @GetMapping("/{shoppingListId}/shared")
    public ShoppingList getShoppingListById(@PathVariable Long shoppingListId) {
        Optional<ShoppingList> optionalShoppingList = shoppingListRepository.findById(shoppingListId);
        if (optionalShoppingList.isPresent()){
            return optionalShoppingList.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ShoppingList not found with id " + shoppingListId);
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

    @PutMapping("/ingredients/{id}")
    public IngredientShoppingList update(@PathVariable Long id, @RequestBody IngredientShoppingList ingredientUpdate) {
        IngredientShoppingList ingredient = this.ingredientShoppingListRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        BeanUtils.copyNonNullProperties(ingredientUpdate, ingredient);

        return this.ingredientShoppingListRepository.save(ingredient);
    }

    @PutMapping("/shared/{id}")
    public ShoppingList update(@PathVariable Long id, @RequestBody ShoppingList shopListUpdate) {
        ShoppingList shoppingList = this.shoppingListRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        BeanUtils.copyNonNullProperties(shopListUpdate, shoppingList);

        return this.shoppingListRepository.save(shoppingList);
    }

    @DeleteMapping("/ingredients/{id}")
    public void delete(@PathVariable Long id) {
        Optional<IngredientShoppingList> optionalIngredient = ingredientShoppingListRepository.findById(id);
        if (optionalIngredient.isPresent()){
            this.ingredientShoppingListRepository.deleteById(id);
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
