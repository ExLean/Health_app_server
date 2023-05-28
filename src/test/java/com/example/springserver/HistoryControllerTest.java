package com.example.springserver;

import com.example.springserver.controllers.HistoryController;
import com.example.springserver.models.History;
import com.example.springserver.models.User;
import com.example.springserver.models.requests.DateRequest;
import com.example.springserver.services.HistoryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class HistoryControllerTest {

    @Mock
    private HistoryService historyService;

    @InjectMocks
    private HistoryController historyController;

    @Test
    void createHistoryTest() {
        User user = new User();
        user.setId(11);
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        HistoryService.HistoryRequest historyRequest = new HistoryService.HistoryRequest();
        historyRequest.setUserId(user.getId());
        historyRequest.setHistoryDate(java.sql.Date.valueOf("2023-05-06"));

        History history = new History();
        history.setId(111);
        history.setUser(user);

        Mockito.when(historyService.createHistory(historyRequest)).thenReturn(history);

        History newHistory = historyController.createHistory(historyRequest);

        assertEquals("Test", newHistory.getUser().getUsername());
        assertEquals(111, newHistory.getId());
    }

    @Test
    void getHistoryByIdTest() {
        User user = new User();
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        History history = new History();
        history.setId(111);
        history.setUser(user);

        Mockito.when(historyService.getHistoryById(history.getId())).thenReturn(history);

        History foundHistory = historyController.getHistoryById(history.getId());

        assertEquals("Test", foundHistory.getUser().getUsername());
        assertEquals(111, foundHistory.getId());
    }

    @Test
    void getAllHistoryTest() {
        User user = new User();
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        History history1 = new History();
        history1.setId(111);
        history1.setUser(user);

        History history2 = new History();
        history2.setId(222);
        history2.setUser(user);

        List<History> allHistories = new ArrayList<>();
        allHistories.add(history1);
        allHistories.add(history2);

        Mockito.when(historyService.getAllHistory()).thenReturn(allHistories);

        List<History> histories = historyController.getAllHistory();

        assertEquals(111, histories.get(0).getId());
        assertEquals(222, histories.get(1).getId());
    }

    @Test
    void deleteStatsTest() {
        User user = new User();
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        History history = new History();
        history.setId(111);
        history.setUser(user);

        historyController.delete(history.getId());

        Mockito.verify(historyService, Mockito.times(1)).delete(history.getId());
    }

    @Test
    void getCurrentUserTodayHistoryTest() {
        User user = new User();
        user.setId(11);
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        History history = new History();
        history.setId(111);
        history.setUser(user);

        Mockito.when(historyService.getCurrentUserTodayHistory(user.getId())).thenReturn(history);

        History foundHistory = historyController.getCurrentUserTodayHistory(user.getId());

        assertEquals("Test", foundHistory.getUser().getUsername());
        assertEquals(111, foundHistory.getId());
    }

    @Test
    void getHistoryByDateTest() {
        User user = new User();
        user.setId(11);
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        History history = new History();
        history.setId(111);
        history.setUser(user);

        DateRequest dateRequest = new DateRequest();
        dateRequest.setUserId(user.getId());
        dateRequest.setDate("2023-05-06");

        Mockito.when(historyService.getHistoryByDate(dateRequest)).thenReturn(history);

        History foundHistory = historyController.getHistoryByDate(dateRequest);

        assertEquals("Test", foundHistory.getUser().getUsername());
        assertEquals(111, foundHistory.getId());
    }
}
