package it.marcodemartino.hangmanbot.game.stats.dao;

import com.github.jasync.sql.db.QueryResult;
import com.github.jasync.sql.db.RowData;
import it.marcodemartino.hangmanbot.game.database.Database;
import it.marcodemartino.hangmanbot.game.stats.entities.UserData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class UserDataDAO implements DAO<UserData> {

    private final Map<Long, UserData> cache;

    public UserDataDAO() {
        cache = new HashMap<>();
    }

    @Override
    public void initializeCache(List<UserData> userData) {
        for (UserData userStat : userData) {
            cache.put(userStat.getUserId(), userStat);
        }
    }

    @Override
    public boolean isPresent(long userId) {
        return cache.containsKey(userId);
    }

    @Override
    public UserData getOrCreate(long userId) {
        if (isPresent(userId)) return cache.get(userId);
        UserData userData = new UserData(userId, "", Locale.ENGLISH);
        insert(userData);
        return userData;
    }

    @Override
    public UserData get(long id) throws SQLException {
        return cache.get(id);
    }

    @Override
    public List<UserData> getAll() throws ExecutionException, InterruptedException {
        String query = "SELECT * FROM user_data";
        CompletableFuture<QueryResult> future = Database.sendPreparedStatement(query);
        QueryResult queryResult = future.get();
        List<UserData> userDataList = new ArrayList<>();
        for (RowData row : queryResult.getRows()) {
            UserData userData = new UserData(
                    row.getLong(0),
                    row.getString(1),
                    Locale.forLanguageTag(row.getString(2)));
            userDataList.add(userData);
        }
        return userDataList;
    }

    @Override
    public CompletableFuture<QueryResult> insert(UserData userData) {
        cache.put(userData.getUserId(), userData);
        String sql = "INSERT INTO user_data (user_id, name) VALUES (?, ?);";
        CompletableFuture<QueryResult> future = Database.sendPreparedStatement(
                sql,
                userData.getUserId(),
                userData.getName()
                );
        return future;
    }

    @Override
    public CompletableFuture<QueryResult> update(UserData userData) {
        cache.put(userData.getUserId(), userData);
        String sql = "UPDATE user_data SET name = ?, locale = ? WHERE user_id = ?;";
        CompletableFuture<QueryResult> future = Database.sendPreparedStatement(
                sql,
                userData.getName(),
                userData.getLocale().getLanguage(),
                userData.getUserId()
                );
        return future;
    }

    @Override
    public CompletableFuture<QueryResult> delete(UserData userData) {
        cache.remove(userData.getUserId());
        String sql = "DELETE FROM user_data WHERE user_id = ?;";
        CompletableFuture<QueryResult> future = Database.sendPreparedStatement(
                sql,
                userData.getUserId()
        );
        return future;
    }
}
