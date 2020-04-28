package ua.javaexternal_shulzhenko.repair_service.dao;

import ua.javaexternal_shulzhenko.repair_service.dao.result_handler.ResultHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UniversalDAOFactory implements DAO {

    private final static UniversalDAOFactory DAO_FACTORY = new UniversalDAOFactory();
    private UniversalDAOFactory() {
    }

    @Override
    public  <T> T select(Connection connection, String sql, ResultHandler<T> resultSetHandler, Object... parameters) throws SQLException {
        try(Connection currentConnection = connection; PreparedStatement ps = currentConnection.prepareStatement(sql)) {
            insertSQLParams(ps, parameters);
            ResultSet resultSet = ps.executeQuery();
            T entity = resultSetHandler.handleResultSet(resultSet);
            return entity;
        }
    }

    @Override
    public void insert(Connection connection, String sql, Object... parameters) throws SQLException {
        try(Connection currentConnection = connection; PreparedStatement ps = currentConnection.prepareStatement(sql)) {
            insertSQLParams(ps, parameters);
            ps.execute();
        }
    }

    @Override
    public void update(Connection connection, String sql, Object... parameters) {

    }

    @Override
    public void delete(Connection connection, String sql, Object... parameters) {

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
