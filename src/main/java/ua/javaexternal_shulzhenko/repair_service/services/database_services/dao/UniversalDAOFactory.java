package ua.javaexternal_shulzhenko.repair_service.services.database_services.dao;

import ua.javaexternal_shulzhenko.repair_service.services.database_services.result_handler.ResultHandler;

import java.sql.*;
import java.util.List;

public class UniversalDAOFactory implements DAO {

    private final static UniversalDAOFactory DAO_FACTORY = new UniversalDAOFactory();

    private UniversalDAOFactory() {
    }

    @Override
    public <T> T select(Connection connection, String sql, ResultHandler<T> resultSetHandler, Object... parameters) throws SQLException {
        try (Connection currentConnection = connection; PreparedStatement ps = currentConnection.prepareStatement(sql)) {
            insertSQLParams(ps, parameters);
            ResultSet resultSet = ps.executeQuery();
            return resultSetHandler.handleResultSet(resultSet);
        }
    }

    @Override
    public void insert(Connection connection, String sql, Object... parameters) throws SQLException {
        try (Connection currentConnection = connection; PreparedStatement ps = currentConnection.prepareStatement(sql)) {
            insertSQLParams(ps, parameters);
            ps.execute();
        }
    }

    @Override
    public void update(Connection connection, String sql, Object... parameters) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            insertSQLParams(ps, parameters);
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(Connection connection, String sql, Object... parameters) throws SQLException {
        try(Connection currentConnection = connection; PreparedStatement ps = currentConnection.prepareStatement(sql)){
            insertSQLParams(ps, parameters);
            ps.execute();
        }
    }

    @Override
    public void insertSQLParams(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
    }

    public static UniversalDAOFactory getDaoFactory() {
        return DAO_FACTORY;
    }

}
