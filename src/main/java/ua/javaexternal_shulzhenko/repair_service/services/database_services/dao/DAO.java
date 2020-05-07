package ua.javaexternal_shulzhenko.repair_service.services.database_services.dao;

import ua.javaexternal_shulzhenko.repair_service.services.database_services.result_handler.ResultHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface DAO {
    void insert(Connection connection, String sql, Object... parameters) throws SQLException;
    <T> T select(Connection connection, String sql, ResultHandler<T> resultSetHandler, Object... parameters) throws SQLException;
    void update(Connection connection, String sql, Object... parameters) throws SQLException;
    void delete(Connection connection, String sql, Object... parameters) throws SQLException;
    void insertSQLParams(PreparedStatement preparedStatement, Object... parameters) throws SQLException;
}
