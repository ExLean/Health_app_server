package com.example.springserver.services;

import com.example.springserver.exceptions.FoodAlreadyExistsException;
import com.example.springserver.exceptions.FoodNotFoundException;
import com.example.springserver.models.Food;
import com.example.springserver.repositories.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public Food createFood(Food food) {
        List<Food> allFoods = getAllFood();

        for (Food fd : allFoods) {
            if (food.getName() != null && fd.getName().equals(food.getName())) {
                throw new FoodAlreadyExistsException("Food with name: " + food.getName() + " already exists.");
            }
        }

        return foodRepository.save(food);
    }

    public Food getFoodById(int foodId) {
        return foodRepository
                .findById(foodId)
                .orElseThrow(() ->
                        new FoodNotFoundException("Food with id: " + foodId + " does not exist."));
    }

    public List<Food> getAllFood() {
        List<Food> foods = new ArrayList<>();
        Streamable.of(foodRepository.findAll()).forEach(foods::add);
        return foods;
    }

    public void delete(int foodId) {
        foodRepository.delete(getFoodById(foodId));
    }
}
