package ua.javaexternal_shulzhenko.repair_service.dao;

import ua.javaexternal_shulzhenko.repair_service.dao.result_handler.ResultHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DAO {
    void insert(Connection connection, String sql, Object... parameters) throws SQLException;
    <T> T select(Connection connection, String sql, ResultHandler<T> resultSetHandler, Object... parameters) throws SQLException;
    void update(Connection connection, String sql, Object... parameters);
    void delete(Connection connection, String sql, Object... parameters);
    void insertSQLParams(PreparedStatement preparedStatement, Object... parameters) throws SQLException;
}
