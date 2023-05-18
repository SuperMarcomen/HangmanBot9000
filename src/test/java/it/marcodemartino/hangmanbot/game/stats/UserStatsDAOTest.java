package it.marcodemartino.hangmanbot.game.stats;

import com.github.jasync.sql.db.QueryResult;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class UserStatsDAOTest {

    private final UserStats userStats = new UserStats(123, 1, 2, 3, 4);
    private final UserStatsDAO userStatsDAO = new UserStatsDAO();

    @Test
    @Order(1)
    void insert() throws ExecutionException, InterruptedException {
        QueryResult queryResult = userStatsDAO.insert(userStats).get();
        assertEquals(1, queryResult.getRowsAffected());
    }

    @Test
    @Order(2)
    void getAll() throws ExecutionException, InterruptedException {
        assertFalse(userStatsDAO.getAll().isEmpty());
    }

    @Test
    @Order(3)
    void delete() throws ExecutionException, InterruptedException {
        QueryResult queryResult = userStatsDAO.delete(userStats).get();
        assertEquals(1, queryResult.getRowsAffected());
    }
}