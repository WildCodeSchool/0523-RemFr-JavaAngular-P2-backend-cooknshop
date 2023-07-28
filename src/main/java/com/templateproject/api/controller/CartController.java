package com.templateproject.api.controller;

import com.templateproject.api.entity.*;
import com.templateproject.api.repository.CartRepository;
import com.templateproject.api.repository.RecipeCartRepository;
import com.templateproject.api.repository.RecipeRepository;
import com.templateproject.api.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    public final CartRepository cartRepository;
    public final RecipeCartRepository recipeCartRepository;
    public final RecipeRepository recipeRepository;
    public final UserRepository userRepository;

    public CartController(CartRepository cartRepository, RecipeCartRepository recipeCartRepository,
                          RecipeRepository recipeRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.recipeCartRepository = recipeCartRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    //Ajouter création cart quand user fini
    @GetMapping("/{cartID}")
    public List<RecipeCart> showIndividualCart(
            @PathVariable("cartID") Long cartId
    ) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cart not found with id " + cartId));
        return cart.getRecipeList();
    }

    @PostMapping("/{cartId}/addRecipe/{id}")
    public Cart addRecipeToCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("id") Long id,
            @RequestParam("nb_person") int nbPerson
    ) {
        Cart cart = this.cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cart not found with id " + cartId));

        Recipe recipe = this.recipeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Recipe not found with id " + id));

        RecipeCart recipeCart = new RecipeCart();
        recipeCart.setRecipe(recipe);
        recipeCart.setRecipeCart(cart);
        recipeCart.setNbPerson(nbPerson);

        this.recipeCartRepository.save(recipeCart);

        return cart;
    }
    @DeleteMapping("/{cartId}/recipe/{id}")
    public Cart removeRecipeFromCart(
            @PathVariable("cartId") Long cartId,
            @PathVariable("id") Long id
    ) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cart not found with id " + cartId));

        RecipeCart recipeCart = recipeCartRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "RecipeCart not found with id " + id));

        if (!cart.getRecipeList().contains(recipeCart)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Recipe not found in the cart.");
        }

        cart.getRecipeList().remove(recipeCart);
        recipeCart.setRecipeCart(null);

        cartRepository.save(cart);
        recipeCartRepository.delete(recipeCart);

        return cart;
    }
    @DeleteMapping("/{cartId}/recipe/empty")
    public Cart emptyCart(@PathVariable("cartId") Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found with id " + cartId));

        List<RecipeCart> recipeCarts = cart.getRecipeList();
        for (RecipeCart recipeCart : recipeCarts) {
            recipeCart.setRecipe(null);
            recipeCart.setRecipeCart(null);
            recipeCartRepository.delete(recipeCart);
        }

        recipeCarts.clear();
        cartRepository.save(cart);

        return cart;
    }
    @GetMapping("/user/{userId}")
    public Long getCartByUserId(@PathVariable("userId") Long userId) {
        Cart cart = cartRepository.findByUser_Id(userId);
        if (cart == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found for user with id " + userId);
        }
        return cart.getId();
    }
}
