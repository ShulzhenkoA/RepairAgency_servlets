package ua.javaexternal_shulzhenko.car_repair_agency.services.database_services.result_handler;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultHandler<T> {
    T handleResultSet(ResultSet resultSet) throws SQLException;
}
