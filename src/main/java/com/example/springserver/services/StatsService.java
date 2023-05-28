package com.example.springserver.services;

import com.example.springserver.exceptions.StatsAlreadyCreatedException;
import com.example.springserver.exceptions.StatsNotFoundException;
import com.example.springserver.exceptions.UserNotFoundException;
import com.example.springserver.models.Stats;
import com.example.springserver.models.User;
import com.example.springserver.models.requests.DateRequest;
import com.example.springserver.repositories.StatsRepository;
import com.example.springserver.repositories.UserRepository;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Service
public class StatsService {

    @Autowired
    private StatsRepository statsRepository;

    @Autowired
    private UserRepository userRepository;

    public Stats createStats(StatsRequest request) {
        Stats stats = new Stats();
        List<Stats> allStats = new ArrayList<>();
        Streamable.of(statsRepository.findAll()).forEach(allStats::add);
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id: " + request.getUserId() + ", does not exist."));
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(request.getStatsDate());

        for (Stats stat : allStats) {
            cal1.setTime(stat.getDate());
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                    && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                if (stat.getUser().getId() == request.getUserId()) {
                    throw new StatsAlreadyCreatedException("Stats with date: "
                            + request.getStatsDate() + ", already exists.");
                }
            }
        }
        stats.setUser(user);
        stats.setDailyCalorieIntake(request.getDailyCalorieIntake());
        stats.setDate(request.getStatsDate());

        return statsRepository.save(stats);
    }

    public Stats updateStat(StatsRequest request) {
        Stats newStats = getStatsById(request.getStatsId());

        if (request.getAmountOfCups() != 0) {
            newStats.setAmountOfCups(request.getAmountOfCups());
        }
        if (request.getLeftCalories() != 0) {
            newStats.setLeftCalories(request.getLeftCalories());
        }
        if (request.getDailyCalorieIntake() != 0) {
            newStats.setDailyCalorieIntake(request.getDailyCalorieIntake());
        }
        if (request.getWeight() != 0) {
            newStats.setWeight(request.getWeight());
        }
        if (request.getCarbAmount() != 0) {
            newStats.setCarbAmount(request.getCarbAmount());
        }
        if (request.getFatAmount() != 0) {
            newStats.setFatAmount(request.getFatAmount());
        }
        if (request.getProteinAmount() != 0) {
            newStats.setProteinAmount(request.getProteinAmount());
        }

        return statsRepository.save(newStats);
    }

    public Stats getStatsByDate(DateRequest request) {
        List<Stats> allStats = new ArrayList<>();
        Streamable.of(statsRepository.findAll()).forEach(allStats::add);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(Date.valueOf(LocalDate.parse(request.getDate())));

        for (Stats stat : allStats) {
            if (stat.getUser().getId() == request.getUserId()) {
                cal1.setTime(stat.getDate());
                if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                        && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                    return stat;
                }
            }
        }
        return getCurrentUserTodayStats(request.getUserId());
    }

    public static class StatsRequest {
        private int statsId;
        private int userId;
        private Date statsDate;
        private float weight;
        private float dailyCalorieIntake;
        private int amountOfCups;
        private float leftCalories;
        private float carbAmount;
        private float fatAmount;
        private float proteinAmount;

        public void setWeight(float weight) {
            this.weight = weight;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setStatsDate(Date statsDate) {
            this.statsDate = statsDate;
        }

        public void setDailyCalorieIntake(float dailyCalorieIntake) {
            this.dailyCalorieIntake = dailyCalorieIntake;
        }

        public int getStatsId() {
            return statsId;
        }

        public void setStatsId(int statsId) {
            this.statsId = statsId;
        }

        public int getUserId() {
            return userId;
        }

        public Date getStatsDate() {
            return statsDate;
        }

        public float getWeight() {
            return weight;
        }

        public float getDailyCalorieIntake() {
            return dailyCalorieIntake;
        }

        public int getAmountOfCups() {
            return amountOfCups;
        }

        public float getLeftCalories() {
            return leftCalories;
        }

        public float getCarbAmount() {
            return carbAmount;
        }

        public float getFatAmount() {
            return fatAmount;
        }

        public float getProteinAmount() {
            return proteinAmount;
        }
    }

    public Stats getStatsById(int statsId) {
        return statsRepository
                .findById(statsId)
                .orElseThrow(() ->
                        new StatsNotFoundException("Stats with id: " + statsId + ", does not exist."));
    }

    public List<Stats> getAllStats() {
        List<Stats> allStats = new ArrayList<>();
        Streamable.of(statsRepository.findAll()).forEach(allStats::add);
        return allStats;
    }

    public void delete(int statsId) {
        statsRepository.delete(getStatsById(statsId));
    }

    public Stats getCurrentUserTodayStats(int userId) {
        List<Stats> allStats = new ArrayList<>();
        Streamable.of(statsRepository.findAll()).forEach(allStats::add);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date(System.currentTimeMillis()));

        for (Stats stat : allStats) {
            if (stat.getUser().getId() == userId) {
                cal1.setTime(stat.getDate());
                if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                        && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                    return stat;
                }
            }
        }

        Calendar calYesterday = Calendar.getInstance();
        calYesterday.setTime(yesterday());

        StatsRequest newStats = new StatsRequest();
        newStats.setUserId(userId);
        float dCalories = 0;
        float dWeight = 0;
        for (Stats stat : allStats) {
            if (stat.getUser().getId() == userId) {
                cal1.setTime(stat.getDate());
                if (cal1.get(Calendar.YEAR) == calYesterday.get(Calendar.YEAR)
                        && cal1.get(Calendar.DAY_OF_YEAR) == calYesterday.get(Calendar.DAY_OF_YEAR)) {
                    dCalories = stat.getDailyCalorieIntake();
                    dWeight = stat.getWeight();
                }
            }
        }
        if (dCalories == 0) {
            newStats.setDailyCalorieIntake(2000);
        } else {
            newStats.setDailyCalorieIntake(dCalories);
        }
        if (dWeight != 0) {
            newStats.setWeight(dWeight);
        }
        newStats.setStatsDate(new Date(System.currentTimeMillis()));

        return createStats(newStats);
    }

    private Date yesterday() {
        return new Date(System.currentTimeMillis() - 24 * 60 * 60 * 1000);
    }

    public List<Stats> getAllCurrentUserStatsHistory(int userId) {
        List<Stats> allUserStats = new ArrayList<>();
        List<Stats> allStats = new ArrayList<>();
        Streamable.of(statsRepository.findAll()).forEach(allStats::add);

        for (Stats stats : allStats) {
            if (stats.getUser().getId() == userId) {
                allUserStats.add(stats);
            }
        }
        return allUserStats;
    }
}
