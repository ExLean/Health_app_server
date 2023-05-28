package com.example.springserver.services;

import com.example.springserver.exceptions.HistoryAlreadyCreatedException;
import com.example.springserver.exceptions.HistoryNotFoundException;
import com.example.springserver.exceptions.MealNotFoundException;
import com.example.springserver.models.*;
import com.example.springserver.models.type.Metric;
import com.example.springserver.repositories.HistoryRepository;
import com.example.springserver.repositories.MealRepository;
import com.example.springserver.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MealService {
    
    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private ProductRepository productRepository;

    public Meal createMeal(MealRequest request) {
        Meal meal = new Meal();

        History history = historyRepository
                .findById(request.getHistoryId())
                .orElseThrow(() -> new HistoryNotFoundException("History with id: " + request.getHistoryId() + " does not exist."));

        meal.setTitle(request.getTitle());
        meal.setCreator(request.getCreator());
        if (request.getInfo() != null) {
            meal.setInfo(request.getInfo());
        }
        if (request.getCookingTime() != 0) {
            meal.setCookingTime(request.getCookingTime());
        }
        meal.setAmount(request.getMealAmount());
        meal.setMetric(request.getMealMetric());

        meal.getHistories().add(history);
        history.getMeals().add(meal);

        return mealRepository.save(meal);
    }

    public Meal updateMeal(MealRequest request) {
        Meal newMeal = getMealById(request.getMealId());
        List<History> histories = newMeal.getHistories();

        if (request.getTitle() != null) {
            newMeal.setTitle(request.getTitle());
        }
        if (request.getInfo() != null) {
            newMeal.setInfo(request.getInfo());
        }
        if (request.getCookingTime() != 0) {
            newMeal.setCookingTime(request.getCookingTime());
        }
        if (request.getCreator() != null) {
            newMeal.setCreator(request.getCreator());
        }
        if (request.getMealAmount() != 0) {
            newMeal.setAmount(request.getMealAmount());
        }
        if (request.getMealMetric() != null) {
            newMeal.setMetric(request.getMealMetric());
        }

        if (request.getHistoryId() != 0) {
            for (History his : histories) {
                if (his.getId() == request.getHistoryId()) {
                    throw new HistoryAlreadyCreatedException("History with id: " + request.getHistoryId() + " is already assigned to meal with id: " + request.getMealId());
                }
            }
            History history = historyRepository
                    .findById(request.getHistoryId())
                    .orElseThrow(() -> new HistoryNotFoundException("History with id: " + request.getHistoryId() + " does not exist."));

            newMeal.getHistories().add(history);
            history.getMeals().add(newMeal);
        }

        return mealRepository.save(newMeal);
    }


    public static class MealRequest {
        private int mealId;
        private int historyId;
        private String title;
        private String info;
        private int cookingTime;
        private String creator;
        private float mealAmount;
        private Metric mealMetric;

        public Metric getMealMetric() {
        return mealMetric;
    }

        public float getMealAmount() {
            return mealAmount;
        }

        public int getMealId() {
            return mealId;
        }

        public int getHistoryId() {
            return historyId;
        }

        public String getTitle() {
            return title;
        }

        public String getInfo() {
            return info;
        }

        public int getCookingTime() {
            return cookingTime;
        }

        public String getCreator() {
            return creator;
        }

        public void setMealId(int mealId) {
            this.mealId = mealId;
        }

        public void setHistoryId(int historyId) {
            this.historyId = historyId;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public void setCookingTime(int cookingTime) {
            this.cookingTime = cookingTime;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public void setMealAmount(float mealAmount) {
            this.mealAmount = mealAmount;
        }

        public void setMealMetric(Metric mealMetric) {
            this.mealMetric = mealMetric;
        }
    }

    public Meal getMealById(int mealId) {
        return mealRepository
                .findById(mealId)
                .orElseThrow(() ->
                        new MealNotFoundException("Meal with id: " + mealId + ", does not exist."));

    }

    public List<Meal> getAllMeals() {
        List<Meal> meals = new ArrayList<>();
        Streamable.of(mealRepository.findAll()).forEach(meals::add);
        return meals;
    }

    public void delete(int mealId) {
        mealRepository.delete(getMealById(mealId));
    }
}
