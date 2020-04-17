package ua.javaexternal_shulzhenko.repair_service.services;

import ua.javaexternal_shulzhenko.repair_service.dao.UniversalDAOFactory;
import ua.javaexternal_shulzhenko.repair_service.dao.Queries;
import ua.javaexternal_shulzhenko.repair_service.dao.result_handler.ResultTemplate;
import ua.javaexternal_shulzhenko.repair_service.dao.result_handler.ResultHandlerFactory;
import ua.javaexternal_shulzhenko.repair_service.exceptions.DataBaseInteractionException;
import ua.javaexternal_shulzhenko.repair_service.models.User;
import ua.javaexternal_shulzhenko.repair_service.utils.DBConnectionsPool;

import java.sql.SQLException;
import java.util.LinkedList;


public class UserDBService {

    private static UniversalDAOFactory DAO_FACTORY = UniversalDAOFactory.getDaoFactory();

    public static void addUser(User user) {
        LinkedList<Object> userFields = new LinkedList<>();
        extractUserFields(user, userFields);
        try {
            DAO_FACTORY.insert(DBConnectionsPool.getConnection(),
                    Queries.INSERT_USER.getQuery(), userFields.toArray());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't add user to database because of: " + exc.getMessage(), exc);
        }
    }

    private static void extractUserFields(User user, LinkedList<Object> userFields) {
        userFields.add(user.getFirstName());
        userFields.add(user.getLastName());
        userFields.add(user.getEmail());
        userFields.add(user.getPassword());
        userFields.add(user.getLanguage());
        userFields.add(user.getRole().name());
    }

    public static User getUser(String email) {
        try {
            return (User) DAO_FACTORY.select(DBConnectionsPool.getConnection(), Queries.SELECT_USER_BY_EMAIL.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.USER), email);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get user from database because of: " + exc.getMessage(), exc);
        }
    }
}
