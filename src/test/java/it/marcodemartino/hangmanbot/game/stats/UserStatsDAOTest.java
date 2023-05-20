package it.marcodemartino.hangmanbot.game.stats;

import com.github.jasync.sql.db.QueryResult;
import it.marcodemartino.hangmanbot.game.stats.dao.DAO;
import it.marcodemartino.hangmanbot.game.stats.dao.UserDataDAO;
import it.marcodemartino.hangmanbot.game.stats.dao.UserStatsDAO;
import it.marcodemartino.hangmanbot.game.stats.entities.UserData;
import it.marcodemartino.hangmanbot.game.stats.entities.UserStats;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserStatsDAOTest {

    private final UserData userData = new UserData(1234, "test", Locale.ENGLISH);
    private final UserStats userStats = new UserStats(1234, 1, 2, 3);
    private final DAO<UserData> userDataDAO = new UserDataDAO();
    private final DAO<UserStats> userStatsDAO = new UserStatsDAO();

    @Test
    @Order(1)
    void insert() throws ExecutionException, InterruptedException {
        QueryResult firstQueryResult = userDataDAO.insert(userData).get();
        assertEquals(1, firstQueryResult.getRowsAffected());

        QueryResult secondQueryResult = userStatsDAO.insert(userStats).get();
        assertEquals(1, secondQueryResult.getRowsAffected());
    }

    @Test
    @Order(2)
    void getAll() throws ExecutionException, InterruptedException {
        assertFalse(userDataDAO.getAll().isEmpty());
        assertFalse(userStatsDAO.getAll().isEmpty());
    }

    @Test
    @Order(3)
    void delete() throws ExecutionException, InterruptedException {
        QueryResult firstQueryResult = userDataDAO.delete(userData).get();
        assertEquals(1, firstQueryResult.getRowsAffected());

        QueryResult secondQueryResult = userStatsDAO.delete(userStats).get();
        assertEquals(1, secondQueryResult.getRowsAffected());


    }
}