package com.example.springserver;

import com.example.springserver.controllers.StatsController;
import com.example.springserver.models.Stats;
import com.example.springserver.models.User;
import com.example.springserver.models.requests.DateRequest;
import com.example.springserver.services.StatsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class StatsControllerTest {

    @Mock
    private StatsService statsService;

    @InjectMocks
    private StatsController statsController;

    @Test
    void createStatsTest() {
        User user = new User();
        user.setId(999);
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        StatsService.StatsRequest stats = new StatsService.StatsRequest();
        stats.setUserId(user.getId());
        stats.setStatsDate(java.sql.Date.valueOf("2023-05-06"));

        Stats stat = new Stats();
        stat.setUser(user);

        Mockito.when(statsService.createStats(stats)).thenReturn(stat);

        Stats newStat = statsController.createStats(stats);

        assertEquals("Test", newStat.getUser().getUsername());
    }

    @Test
    void updateStatsTest() {
        User user = new User();
        user.setId(999);
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        StatsService.StatsRequest stats = new StatsService.StatsRequest();
        stats.setStatsId(111);
        stats.setUserId(user.getId());
        stats.setStatsDate(java.sql.Date.valueOf("2023-05-06"));

        Stats stat = new Stats();
        stat.setId(stats.getStatsId());
        stat.setUser(user);

        Mockito.when(statsService.updateStat(stats)).thenReturn(stat);

        Stats newStat = statsController.updateStats(stats);

        assertEquals("Test", newStat.getUser().getUsername());
        assertEquals(111, newStat.getId());
    }

    @Test
    void getStatsByIdTest() {
        User user = new User();
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        Stats stats = new Stats();
        stats.setId(111);
        stats.setUser(user);

        Mockito.when(statsService.getStatsById(stats.getId())).thenReturn(stats);

        Stats foundStats = statsController.getStatsById(stats.getId());

        assertEquals("Test", foundStats.getUser().getUsername());
        assertEquals(111, foundStats.getId());
    }

    @Test
    void getAllStats() {
        User user = new User();
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        Stats stats1 = new Stats();
        stats1.setId(111);
        stats1.setUser(user);

        Stats stats2 = new Stats();
        stats2.setId(222);
        stats2.setUser(user);

        List<Stats> allStats = new ArrayList<>();
        allStats.add(stats1);
        allStats.add(stats2);

        Mockito.when(statsService.getAllStats()).thenReturn(allStats);

        List<Stats> stats = statsController.getAllStats();

        assertEquals(111, stats.get(0).getId());
        assertEquals(222, stats.get(1).getId());
    }

    @Test
    void deleteStatsTest() {
        User user = new User();
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        Stats stats = new Stats();
        stats.setId(111);
        stats.setUser(user);

        statsController.delete(stats.getId());

        Mockito.verify(statsService, Mockito.times(1)).delete(stats.getId());
    }

    @Test
    void getCurrentUserTodayStatsTest() {
        User user = new User();
        user.setId(11);
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        Stats stats = new Stats();
        stats.setId(111);
        stats.setUser(user);

        Mockito.when(statsService.getCurrentUserTodayStats(user.getId())).thenReturn(stats);

        Stats stat = statsController.getCurrentUserTodayStats(user.getId());

        assertEquals("Test", stat.getUser().getUsername());
        assertEquals(111, stat.getId());
    }

    @Test
    void getAllCurrentUserStatsHistoryTest() {
        User user = new User();
        user.setId(11);
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        Stats stats1 = new Stats();
        stats1.setId(111);
        stats1.setUser(user);

        Stats stats2 = new Stats();
        stats2.setId(222);
        stats2.setUser(user);

        List<Stats> allUserStats = new ArrayList<>();
        allUserStats.add(stats1);
        allUserStats.add(stats2);

        Mockito.when(statsService.getAllCurrentUserStatsHistory(user.getId())).thenReturn(allUserStats);

        List<Stats> stats = statsController.getAllCurrentUserStatsHistory(user.getId());

        assertEquals(111, stats.get(0).getId());
        assertEquals(222, stats.get(1).getId());
    }

    @Test
    void getStatsByDateTest() {
        User user = new User();
        user.setId(11);
        user.setUsername("Test");
        user.setPassword("123");
        user.setEmail("test@gmail.com");
        user.setBirthday(java.sql.Date.valueOf("2000-06-13"));
        user.setHeight(172.5F);

        Stats stats = new Stats();
        stats.setId(111);
        stats.setUser(user);

        DateRequest dateRequest = new DateRequest();
        dateRequest.setUserId(user.getId());
        dateRequest.setDate("2023-05-06");

        Mockito.when(statsService.getStatsByDate(dateRequest)).thenReturn(stats);

        Stats foundStats = statsController.getStatsByDate(dateRequest);

        assertEquals("Test", foundStats.getUser().getUsername());
        assertEquals(111, foundStats.getId());
    }
}
