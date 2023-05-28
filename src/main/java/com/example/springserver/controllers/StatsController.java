package com.example.springserver.controllers;

import com.example.springserver.models.Stats;
import com.example.springserver.models.requests.DateRequest;
import com.example.springserver.services.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StatsController {

    @Autowired
    private StatsService service;

    @PostMapping("/stats/create")
    public Stats createStats(@RequestBody StatsService.StatsRequest request) {
        return service.createStats(request);
    }

    @PutMapping("/stats/update")
    public Stats updateStats(@RequestBody StatsService.StatsRequest request) {
        return service.updateStat(request);
    }

    @GetMapping("/stats/{id}")
    public Stats getStatsById(@PathVariable int id) {
        return service.getStatsById(id);
    }

    @GetMapping("/stats/get-all")
    public List<Stats> getAllStats() {
        return service.getAllStats();
    }

    @DeleteMapping("/stats/delete/{statsId}")
    public void delete(@PathVariable int statsId) {
        service.delete(statsId);
    }

    @GetMapping ("/stats/current/{userId}")
    public Stats getCurrentUserTodayStats(@PathVariable int userId) {
        return service.getCurrentUserTodayStats(userId);
    }

    @GetMapping("/stats/all-current/{userId}")
    public List<Stats> getAllCurrentUserStatsHistory(@PathVariable int userId) {
        return service.getAllCurrentUserStatsHistory(userId);
    }

    @PostMapping("/stats/getByDate")
    public Stats getStatsByDate(@RequestBody DateRequest request) {
        return service.getStatsByDate(request);
    }
}
