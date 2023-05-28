package com.example.springserver.controllers;

import com.example.springserver.models.Food;
import com.example.springserver.services.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodController {

    @Autowired
    private FoodService service;

    @PostMapping("/food/create")
    public Food createFood(@RequestBody Food food) {
        return service.createFood(food);
    }

    @GetMapping("/food/{id}")
    public Food getFoodById(@PathVariable int id) {
        return service.getFoodById(id);
    }

    @GetMapping("/food/get-all")
    public List<Food> getAllFood() {
        return service.getAllFood();
    }

    @DeleteMapping("/food/delete/{foodId}")
    public void delete(@PathVariable int foodId) {
        service.delete(foodId);
    }
}
