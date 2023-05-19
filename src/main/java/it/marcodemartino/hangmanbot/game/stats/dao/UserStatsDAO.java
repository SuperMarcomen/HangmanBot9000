package it.marcodemartino.hangmanbot.game.stats.dao;

import com.github.jasync.sql.db.QueryResult;
import com.github.jasync.sql.db.RowData;
import it.marcodemartino.hangmanbot.game.database.Database;
import it.marcodemartino.hangmanbot.game.stats.entities.UserStats;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class UserStatsDAO implements DAO<UserStats> {

    private final Map<Long, UserStats> cache;

    public UserStatsDAO() {
        cache = new HashMap<>();
    }

    @Override
    public void initializeCache(List<UserStats> userStats) {
        for (UserStats userStat : userStats) {
            cache.put(userStat.getUserId(), userStat);
        }
    }

    @Override
    public boolean isPresent(long userId) {
        return cache.containsKey(userId);
    }

    @Override
    public UserStats get(long id) throws SQLException {
        return cache.get(id);
    }

    @Override
    public List<UserStats> getAll() throws ExecutionException, InterruptedException {
        String query = "SELECT * FROM user_stats";
        CompletableFuture<QueryResult> future = Database.sendPreparedStatement(query);
        QueryResult queryResult = future.get();
        List<UserStats> userStatsList = new ArrayList<>();
        for (RowData row : queryResult.getRows()) {
            UserStats userStats = new UserStats(
                    row.getLong(0),
                    row.getInt(1),
                    row.getInt(2),
                    row.getInt(3),
                    row.getInt(4)
            );
            userStatsList.add(userStats);
        }
        return userStatsList;
    }

    @Override
    public CompletableFuture<QueryResult> insert(UserStats userStats) {
        cache.put(userStats.getUserId(), userStats);
        String sql = "INSERT INTO user_stats (user_id, started_matches, right_letters, wrong_letters, words_guessed) VALUES (?, ?, ?, ?, ?);";
        CompletableFuture<QueryResult> future = Database.sendPreparedStatement(
                sql,
                userStats.getUserId(),
                userStats.getStartedMatches(),
                userStats.getRightLetters(),
                userStats.getWrongLetters(),
                userStats.getGuessedWords()
                );
        return future;
    }

    @Override
    public CompletableFuture<QueryResult> update(UserStats userStats) {
        cache.put(userStats.getUserId(), userStats);
        String sql = "UPDATE user_stats SET started_matches = ?, right_letters = ?, wrong_letters = ?, words_guessed = ? WHERE user_id = ?;";
        CompletableFuture<QueryResult> future = Database.sendPreparedStatement(
                sql,
                userStats.getStartedMatches(),
                userStats.getRightLetters(),
                userStats.getWrongLetters(),
                userStats.getGuessedWords(),
                userStats.getUserId()
                );
        return future;
    }

    @Override
    public CompletableFuture<QueryResult> delete(UserStats userStats) {
        cache.remove(userStats.getUserId());
        String sql = "DELETE FROM user_stats WHERE user_id = ?;";
        CompletableFuture<QueryResult> future = Database.sendPreparedStatement(
                sql,
                userStats.getUserId()
        );
        return future;
    }
}
