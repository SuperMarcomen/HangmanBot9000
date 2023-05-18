package it.marcodemartino.hangmanbot.game.database;

import com.github.jasync.sql.db.Connection;
import com.github.jasync.sql.db.QueryResult;
import com.github.jasync.sql.db.mysql.MySQLConnectionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/hangmanbot";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final Connection connection = MySQLConnectionBuilder.createConnectionPool(
            URL + "?user=" + USER  + "&password=" + PASSWORD);

    private Database() {}

    public static CompletableFuture<QueryResult> sendPreparedStatement(String sql, Object... args) {
        return connection.sendPreparedStatement(sql, List.of(args));
    }

    public static void closeConnection() throws ExecutionException, InterruptedException {
        connection.disconnect().get();
    }

}
