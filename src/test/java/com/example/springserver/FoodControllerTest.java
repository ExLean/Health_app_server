package com.example.springserver;

import com.example.springserver.controllers.FoodController;
import com.example.springserver.models.Food;
import com.example.springserver.services.FoodService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class FoodControllerTest {

    @Mock
    private FoodService foodService;

    @InjectMocks
    private FoodController foodController;

    @Test
    void createFoodTest() {
        Food food = new Food();
        food.setId(111);
        food.setName("Bananas");
        food.setCalories(50F);
        food.setCarbs(55F);
        food.setProtein(12F);
        food.setFat(2F);

        Mockito.when(foodService.createFood(food)).thenReturn(food);

        Food newFood = foodController.createFood(food);

        assertEquals("Bananas", newFood.getName());
        assertEquals(111, newFood.getId());
    }

    @Test
    void getFoodByIdTest() {
        Food food = new Food();
        food.setId(111);
        food.setName("Bananas");
        food.setCalories(50F);
        food.setCarbs(55F);
        food.setProtein(12F);
        food.setFat(2F);

        Mockito.when(foodService.getFoodById(food.getId())).thenReturn(food);

        Food foundFood = foodController.getFoodById(food.getId());

        assertEquals("Bananas", foundFood.getName());
        assertEquals(111, foundFood.getId());
    }

    @Test
    void getAllFoodTest() {
        Food food1 = new Food();
        food1.setId(111);
        food1.setName("Bananas");
        food1.setCalories(50F);
        food1.setCarbs(55F);
        food1.setProtein(12F);
        food1.setFat(2F);

        Food food2 = new Food();
        food2.setId(222);
        food2.setName("Obuolys");
        food2.setCalories(45F);
        food2.setCarbs(40F);
        food2.setProtein(8F);
        food2.setFat(1F);

        List<Food> allFood = new ArrayList<>();
        allFood.add(food1);
        allFood.add(food2);

        Mockito.when(foodService.getAllFood()).thenReturn(allFood);

        List<Food> foundAllFood = foodController.getAllFood();

        assertEquals(111, foundAllFood.get(0).getId());
        assertEquals(222, foundAllFood.get(1).getId());
    }

    @Test
    void deleteFoodTest() {
        Food food = new Food();
        food.setId(111);
        food.setName("Bananas");
        food.setCalories(50F);
        food.setCarbs(55F);
        food.setProtein(12F);
        food.setFat(2F);

        foodController.delete(food.getId());

        Mockito.verify(foodService, Mockito.times(1)).delete(food.getId());
    }
}
