package com.templateproject.api.controller;

import com.templateproject.api.entity.Ingredient;
import com.templateproject.api.repository.IngredientRepository;
import com.templateproject.api.utils.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class IngredientController {

    private final IngredientRepository ingredientRepository;

    public IngredientController(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @GetMapping("/ingredients")
    public List<Ingredient> getAllIngredient(){
        return this.ingredientRepository.findAll();
    }

    @GetMapping("/ingredients/{id}")
    public Ingredient getIngredient(@PathVariable Long id){
        return this.ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/ingredients")
    public Ingredient create(@RequestBody Ingredient newIngredient){
        return this.ingredientRepository.save(newIngredient);
    }

    @PutMapping("/ingredients/{id}")
    public Ingredient update(@PathVariable Long id, @RequestBody Ingredient updatedIngredient) {
        Ingredient ingredientToUpdate = ingredientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found"));

        BeanUtils.copyNonNullProperties(updatedIngredient, ingredientToUpdate);

        return ingredientRepository.save(ingredientToUpdate);
    }

    @DeleteMapping("/ingredients/{id}")
    public boolean delete(@PathVariable Long id){
        ingredientRepository.deleteById(id);
        return true;
    }
}
