package it.marcodemartino.hangmanbot.game.stats.dao;

import com.github.jasync.sql.db.QueryResult;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface DAO<T> {

    void initializeCache(List<T> t);

    boolean isPresent(long id);

    T get(long id) throws SQLException;

    List<T> getAll() throws ExecutionException, InterruptedException;

    CompletableFuture<QueryResult> insert(T t);

    CompletableFuture<QueryResult> update(T t);

    CompletableFuture<QueryResult> delete(T t);
}
