package com.example.springserver.controllers;

import com.example.springserver.models.Meal;
import com.example.springserver.services.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MealController {

    @Autowired
    private MealService mealService;

    @PostMapping("/meal/create")
    public Meal createMeal(@RequestBody MealService.MealRequest request) {
        return mealService.createMeal(request);
    }

    @PutMapping("/meal/update")
    public Meal updateMeal(@RequestBody MealService.MealRequest request) {
        return mealService.updateMeal(request);
    }

    @GetMapping("/meal/{mealId}")
    public Meal getMealById(@PathVariable int mealId) {
        return mealService.getMealById(mealId);
    }

    @GetMapping("/meal/get-all")
    public List<Meal> getAllMeals() {
        return mealService.getAllMeals();
    }

    @DeleteMapping("/meal/delete/{mealId}")
    public void delete(@PathVariable int mealId) {
        mealService.delete(mealId);
    }
}
