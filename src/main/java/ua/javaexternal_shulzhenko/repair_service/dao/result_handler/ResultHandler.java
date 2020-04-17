package ua.javaexternal_shulzhenko.repair_service.dao.result_handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultHandler<T> {
    T handleResultSet(ResultSet resultSet) throws SQLException;
}
