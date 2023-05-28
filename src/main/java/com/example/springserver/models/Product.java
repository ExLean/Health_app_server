package com.example.springserver.models;

import com.example.springserver.models.type.Metric;
import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Food food;

    @Column(name = "meal_id")
    private int mealId;

    @Column(name = "amount", nullable = false)
    private float amount;

    @Column(name = "metric", nullable = false, columnDefinition = "enum('G', 'ML')")
    @Enumerated(EnumType.STRING)
    private Metric metric;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getMealId() {
        return mealId;
    }

    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }
}
