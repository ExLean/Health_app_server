package com.example.springserver.controllers;

import com.example.springserver.models.History;
import com.example.springserver.models.requests.DateRequest;
import com.example.springserver.services.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HistoryController {

    @Autowired
    private HistoryService service;

    @PostMapping("/history/create")
    public History createHistory(@RequestBody HistoryService.HistoryRequest request) {
        return service.createHistory(request);
    }

    @GetMapping("/history/{id}")
    public History getHistoryById(@PathVariable int id) {
        return service.getHistoryById(id);
    }

    @GetMapping("/history/get-all")
    public List<History> getAllHistory() {
        return service.getAllHistory();
    }

    @DeleteMapping("/history/delete/{historyId}")
    public void delete(@PathVariable int historyId) {
        service.delete(historyId);
    }

    @GetMapping("/history/current/{userId}")
    public History getCurrentUserTodayHistory(@PathVariable int userId) {
        return service.getCurrentUserTodayHistory(userId);
    }

    @PostMapping("/history/getByDate")
    public History getHistoryByDate(@RequestBody DateRequest request) {
        return service.getHistoryByDate(request);
    }
}
