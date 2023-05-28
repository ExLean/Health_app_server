package com.example.springserver.services;

import com.example.springserver.exceptions.HistoryAlreadyCreatedException;
import com.example.springserver.exceptions.StatsNotFoundException;
import com.example.springserver.exceptions.UserNotFoundException;
import com.example.springserver.models.History;
import com.example.springserver.models.User;
import com.example.springserver.models.requests.DateRequest;
import com.example.springserver.repositories.HistoryRepository;
import com.example.springserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    public History createHistory(HistoryRequest request) {
        History history = new History();
        List<History> histories = new ArrayList<>();
        Streamable.of(historyRepository.findAll()).forEach(histories::add);
        User user = userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id: " + request.getUserId() + ", does not exist."));
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(request.getHistoryDate());

        for (History hist : histories) {
            cal1.setTime(hist.getDate());
            if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                    && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                if (hist.getUser().getId() == request.getUserId()) {
                    throw new HistoryAlreadyCreatedException("History with date: "
                            + request.getHistoryDate() + ", already exists.");
                }
            }
        }
        history.setUser(user);
        history.setDate(request.getHistoryDate());

        return historyRepository.save(history);
    }

    public History getHistoryByDate(DateRequest request) {
        List<History> histories = new ArrayList<>();
        Streamable.of(historyRepository.findAll()).forEach(histories::add);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(Date.valueOf(LocalDate.parse(request.getDate()))); // 2018-05-05

        for (History history : histories) {
            if (history.getUser().getId() == request.getUserId()) {
                cal1.setTime(history.getDate());
                if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                        && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                    return history;
                }
            }
        }
        return getCurrentUserTodayHistory(request.getUserId());
    }

    public static class HistoryRequest {
        private int historyId;
        private int userId;
        private Date historyDate;

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public void setHistoryDate(Date historyDate) {
            this.historyDate = historyDate;
        }

        public int getHistoryId() {
            return historyId;
        }

        public int getUserId() {
            return userId;
        }

        public Date getHistoryDate() {
            return historyDate;
        }
    }

    public History getHistoryById(int historyId) {
        return historyRepository
                .findById(historyId)
                .orElseThrow(() ->
                        new StatsNotFoundException("Stats with id: " + historyId + ", does not exist."));

    }

    public List<History> getAllHistory() {
        List<History> histories = new ArrayList<>();
        Streamable.of(historyRepository.findAll()).forEach(histories::add);
        return histories;
    }

    public void delete(int historyId) {
        historyRepository.delete(getHistoryById(historyId));
    }

    public History getCurrentUserTodayHistory(int userId) {
        List<History> histories = new ArrayList<>();
        Streamable.of(historyRepository.findAll()).forEach(histories::add);
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date(System.currentTimeMillis()));

        for (History history : histories) {
            if (history.getUser().getId() == userId) {
                cal1.setTime(history.getDate());
                if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                        && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
                    return history;
                }
            }
        }

        HistoryRequest newHistory = new HistoryRequest();

        newHistory.setUserId(userId);
        newHistory.setHistoryDate(new Date(System.currentTimeMillis()));
        return createHistory(newHistory);
    }
}
