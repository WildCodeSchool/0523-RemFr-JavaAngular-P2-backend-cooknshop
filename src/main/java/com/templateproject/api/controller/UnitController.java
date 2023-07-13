package com.templateproject.api.controller;

import com.templateproject.api.entity.Unit;
import com.templateproject.api.repository.UnitRepository;
import com.templateproject.api.utils.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/units")
public class UnitController {

    public final UnitRepository unitRepository;

    public UnitController(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @GetMapping("")
    public List<Unit> getAll() {
        return this.unitRepository.findAll();
    }

    @GetMapping("/{id}")
    public Unit getById(@PathVariable Long id){
        return this.unitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/{nameValue}")
    public Unit getByName(@PathVariable("nameValue") String name){
        return this.unitRepository.findUnitByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/name/containing/{nameValue}")
    public List<Unit> getByNameContaining(@PathVariable("nameValue") String name){
        return this.unitRepository.findUnitsByNameContaining(name);
    }

    @PostMapping("")
    public Unit create(@RequestBody Unit newUnit) {
        return this.unitRepository.save(newUnit);
    }

    @PostMapping("/search")
    public List<Unit> search(@RequestParam String name) {
        return this.unitRepository.findByNameContaining(name);
    }

    @PutMapping("/{id}")
    public Unit update(@PathVariable Long id, @RequestBody Unit unitUpdate) {
        Unit unit = this.unitRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        BeanUtils.copyNonNullProperties(unitUpdate, unit);

        return this.unitRepository.save(unit);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        Optional<Unit> optionalUnit = unitRepository.findById(id);
        if (optionalUnit.isPresent()) {
            this.unitRepository.deleteById(id);
        }
    }
}
