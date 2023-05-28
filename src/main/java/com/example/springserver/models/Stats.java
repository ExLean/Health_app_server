package com.example.springserver.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Stats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @Column(name = "weight")
    private float weight;

    @Column(name = "daily_calorie_intake")
    private float dailyCalorieIntake;

    @Column(name = "amount_of_cups")
    private int amountOfCups;

    @Column(name = "left_calories")
    private float leftCalories;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "carb_amount")
    private float carbAmount;

    @Column(name = "fat_amount")
    private float fatAmount;

    @Column(name = "protein_amount")
    private float proteinAmount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getDailyCalorieIntake() {
        return dailyCalorieIntake;
    }

    public void setDailyCalorieIntake(float dailyCalorieIntake) {
        this.dailyCalorieIntake = dailyCalorieIntake;
    }

    public int getAmountOfCups() {
        return amountOfCups;
    }

    public void setAmountOfCups(int amountOfCups) {
        this.amountOfCups = amountOfCups;
    }

    public float getLeftCalories() {
        return leftCalories;
    }

    public void setLeftCalories(float leftCalories) {
        this.leftCalories = leftCalories;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getCarbAmount() {
        return carbAmount;
    }

    public void setCarbAmount(float carbAmount) {
        this.carbAmount = carbAmount;
    }

    public float getFatAmount() {
        return fatAmount;
    }

    public void setFatAmount(float fatAmount) {
        this.fatAmount = fatAmount;
    }

    public float getProteinAmount() {
        return proteinAmount;
    }

    public void setProteinAmount(float proteinAmount) {
        this.proteinAmount = proteinAmount;
    }
}
