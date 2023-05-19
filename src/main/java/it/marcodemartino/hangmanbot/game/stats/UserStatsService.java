package it.marcodemartino.hangmanbot.game.stats;

import it.marcodemartino.hangmanbot.game.stats.dao.DAO;
import it.marcodemartino.hangmanbot.game.stats.dao.UserDataDAO;
import it.marcodemartino.hangmanbot.game.stats.dao.UserStatsDAO;
import it.marcodemartino.hangmanbot.game.stats.entities.UserData;
import it.marcodemartino.hangmanbot.game.stats.entities.UserInfo;
import it.marcodemartino.hangmanbot.game.stats.entities.UserStats;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class UserStatsService {

    private final DAO<UserStats> userStatsDAO;
    private final DAO<UserData> userDataDAO;

    public UserStatsService() {
        userStatsDAO = new UserStatsDAO();
        userDataDAO = new UserDataDAO();
        loadData();
    }

    private void loadData() {
        List<UserStats> userStats;
        List<UserData> userData;
        try {
            userStats = userStatsDAO.getAll();
            userData = userDataDAO.getAll();
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("An error occurred while loading the data from the database: " + e.getMessage());
            return;
        }
        userStatsDAO.initializeCache(userStats);
        userDataDAO.initializeCache(userData);
    }

    public List<UserInfo> getTopPlayers(int limit) {
        List<UserStats> userStatsList = getUserStats();
        if (userStatsList.isEmpty()) return Collections.emptyList();

        Stream<UserStats> sortedStatsList = sortStats(userStatsList, limit);
        List<UserInfo> userInfolist = new ArrayList<>();

        sortedStatsList.forEach(userStats -> {
            long userId = userStats.getUserId();
            UserData userData = getUserData(userId);
            userInfolist.add(new UserInfo(userStats, userData));
        });

        return userInfolist;
    }

    private List<UserStats> getUserStats() {
        List<UserStats> userStats;
        try {
            userStats = userStatsDAO.getAll();
        } catch (ExecutionException | InterruptedException e) {
            System.err.println("An error occurred while loading the data from the database: " + e.getMessage());
            userStats = Collections.emptyList();
        }
        return userStats;
    }

    private Stream<UserStats> sortStats(List<UserStats> userStats, int limit) {
        return userStats.stream()
                .sorted(Comparator.comparingDouble(UserStats::getRatio).reversed())
                .limit(limit);
    }

    private UserData getUserData(long userId) {
        UserData userData;
        try {
            userData = userDataDAO.get(userId);
        } catch (SQLException e) {
            System.err.println("An error occurred while loading the data from the database: " + e.getMessage());
            userData = new UserData(userId, "");
        }
        return userData;
    }
}
