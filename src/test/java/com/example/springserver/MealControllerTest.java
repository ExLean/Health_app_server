package com.example.springserver;

import com.example.springserver.controllers.MealController;
import com.example.springserver.models.History;
import com.example.springserver.models.Meal;
import com.example.springserver.models.User;
import com.example.springserver.models.type.Metric;
import com.example.springserver.services.MealService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class MealControllerTest {

    @Mock
    private MealService mealService;

    @InjectMocks
    private MealController mealController;

    @Test
    void createMeal() {
        User user = new User();
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        History history = new History();
        history.setId(11);
        history.setUser(user);

        MealService.MealRequest mealRequest = new MealService.MealRequest();
        mealRequest.setHistoryId(history.getId());
        mealRequest.setTitle("TestMeal");
        mealRequest.setMealAmount(100F);
        mealRequest.setMealMetric(Metric.G);

        Meal meal = new Meal();
        meal.setId(111);
        meal.setTitle("TestMeal");
        meal.setAmount(100F);
        meal.setMetric(Metric.G);

        Mockito.when(mealService.createMeal(mealRequest)).thenReturn(meal);

        Meal newMeal = mealController.createMeal(mealRequest);

        assertEquals(111, newMeal.getId());
        assertEquals("TestMeal", newMeal.getTitle());
    }

    @Test
    void updateMealTest() {
        User user = new User();
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        History history = new History();
        history.setId(11);
        history.setUser(user);

        Meal meal = new Meal();
        meal.setId(111);
        meal.setTitle("TestMeal");
        meal.setAmount(100F);
        meal.setMetric(Metric.G);

        MealService.MealRequest mealRequest = new MealService.MealRequest();
        mealRequest.setHistoryId(history.getId());
        mealRequest.setMealId(meal.getId());
        mealRequest.setTitle("TestMeal");
        mealRequest.setMealAmount(100F);
        mealRequest.setMealMetric(Metric.G);

        Mockito.when(mealService.updateMeal(mealRequest)).thenReturn(meal);

        Meal updateMeal = mealController.updateMeal(mealRequest);

        assertEquals(111, updateMeal.getId());
        assertEquals("TestMeal", updateMeal.getTitle());
    }

    @Test
    void getMealByIdTest() {
        Meal meal = new Meal();
        meal.setId(111);
        meal.setTitle("TestMeal");
        meal.setAmount(100F);
        meal.setMetric(Metric.G);

        Mockito.when(mealService.getMealById(meal.getId())).thenReturn(meal);

        Meal foundMeal = mealController.getMealById(meal.getId());

        assertEquals(Metric.G, foundMeal.getMetric());
        assertEquals("TestMeal", foundMeal.getTitle());
    }

    @Test
    void getAllMealsTest() {
        Meal meal1 = new Meal();
        meal1.setId(111);
        meal1.setTitle("TestMeal");
        meal1.setAmount(100F);
        meal1.setMetric(Metric.G);

        Meal meal2 = new Meal();
        meal2.setId(222);
        meal2.setTitle("TestMeal");
        meal2.setAmount(100F);
        meal2.setMetric(Metric.G);

        List<Meal> allMeals = new ArrayList<>();
        allMeals.add(meal1);
        allMeals.add(meal2);

        Mockito.when(mealService.getAllMeals()).thenReturn(allMeals);

        List<Meal> meals = mealController.getAllMeals();

        assertEquals(111, meals.get(0).getId());
        assertEquals(222, meals.get(1).getId());
    }

    @Test
    void deleteMealTest() {
        Meal meal = new Meal();
        meal.setId(111);
        meal.setTitle("TestMeal");
        meal.setAmount(100F);
        meal.setMetric(Metric.G);

        mealController.delete(meal.getId());

        Mockito.verify(mealService, Mockito.times(1)).delete(meal.getId());
    }
}
