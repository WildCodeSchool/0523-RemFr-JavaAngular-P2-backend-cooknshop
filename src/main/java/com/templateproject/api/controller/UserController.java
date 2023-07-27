package com.templateproject.api.controller;

import com.templateproject.api.entity.Cart;
import com.templateproject.api.entity.ShoppingList;
import com.templateproject.api.entity.User;
import com.templateproject.api.repository.CartRepository;
import com.templateproject.api.repository.ShoppingListRepository;
import com.templateproject.api.repository.UserRepository;
import com.templateproject.api.utils.BeanUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    public final UserRepository userRepository;

    public final CartRepository cartRepository;

    public final ShoppingListRepository shoppingListRepository;

    public UserController(UserRepository userRepository, CartRepository cartRepository,
                          ShoppingListRepository shoppingListRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id " + id));
    }

    @GetMapping("/email/{emailValue}")
    public User getByEmail(@PathVariable String emailValue) {
        return this.userRepository.findByEmail(emailValue)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with email " + emailValue));
    }

    @PostMapping("/login")
    public User getUser(@RequestBody User user) {
        Optional<User> optionalUser = this.userRepository.findByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            if (BCrypt.checkpw(user.getPassword(), optionalUser.get().getPassword())) {
                return optionalUser.get();
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Email or/and password are incorrect");
    }

    @PostMapping("/register")
    public User create(@RequestBody User newUser) {

        if (this.userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User already exists with email " + newUser.getEmail());
        }

        newUser.setPassword(BCrypt.hashpw(newUser.getPassword(), BCrypt.gensalt()));
        this.userRepository.save(newUser);

        Cart cart = new Cart();
        cart.setCreationDate(LocalDateTime.now());
        cart.setUser(newUser);
        this.cartRepository.save(cart);

        ShoppingList shoppingList = new ShoppingList();
        shoppingList.setCreationDate(LocalDateTime.now());
        shoppingList.setUser(newUser);
        this.shoppingListRepository.save(shoppingList);

        newUser.setCart(cart);
        newUser.setShoppingList(shoppingList);
        return this.userRepository.save(newUser);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User userInfos) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id " + id));

        String password = user.getPassword();

        BeanUtils.copyNonNullProperties(userInfos, user);

        if (!BCrypt.checkpw(userInfos.getPassword(), password)) {
            user.setPassword(BCrypt.hashpw(userInfos.getPassword(), BCrypt.gensalt()));
        }
        return this.userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            this.userRepository.deleteById(id);
        }
    }
}
