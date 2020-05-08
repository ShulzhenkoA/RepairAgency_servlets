package ua.javaexternal_shulzhenko.repair_service.services.database_services;

import ua.javaexternal_shulzhenko.repair_service.models.forms.UserEditingForm;
import ua.javaexternal_shulzhenko.repair_service.models.user.Role;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.dao.Queries;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.dao.UniversalDAOFactory;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.result_handler.ResultTemplate;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.result_handler.ResultHandlerFactory;
import ua.javaexternal_shulzhenko.repair_service.exceptions.DataBaseInteractionException;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.models.forms.RegistrationForm;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.connection.DBConnectionsPool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;


public class UsersDBService {

    private static final UniversalDAOFactory DAO_FACTORY = UniversalDAOFactory.getDaoFactory();

    public static void createUser(RegistrationForm registrationForm) {
        LinkedList<Object> formFields = new LinkedList<>();
        extractFormFields(registrationForm, formFields);
        try {
            DAO_FACTORY.insert(DBConnectionsPool.getConnection(),
                    Queries.INSERT_USER.getQuery(), formFields.toArray());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't add user to database because of: " + exc.getMessage(), exc);
        }
    }

    private static void extractFormFields(RegistrationForm registrationForm, LinkedList<Object> formFields) {
        formFields.add(registrationForm.getFirstName());
        formFields.add(registrationForm.getLastName());
        formFields.add(registrationForm.getEmail());
        formFields.add(registrationForm.getPassword().hashCode());
        formFields.add(registrationForm.getLanguage());
        formFields.add(registrationForm.getRole().name());
    }

    public static User getUserByID(int id) {
        try {
            return (User) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_USER_BY_ID.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.USER), id);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get user from database because of: " + exc.getMessage(), exc);
        }
    }

    public static List<User> getUsersByRole(Role role) {
        try {
            return (List<User>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_USERS_BY_ROLE.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.USERS), role.name());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get users from database because of: " + exc.getMessage(), exc);
        }
    }

    public static List<User> getUsersByRoleOffsetAmount(Role role, int offset, int amount) {
        try {
            return (List<User>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_USERS_BY_ROLE_OFFSET_AMOUNT.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.USERS),
                    role.name(), offset, amount);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get users from database because of: " + exc.getMessage(), exc);
        }
    }

    public static int getUsersAmountByRole(Role role) {
        try {
            return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_USERS_AMOUNT_BY_ROLE.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT), role.name());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get users amount from database because of: " + exc.getMessage(), exc);
        }
    }

    public static User getUserByEmail(String email) {
        try {
            return (User) DAO_FACTORY.select(DBConnectionsPool.getConnection(), Queries.SELECT_USER_BY_EMAIL.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.USER), email);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get user from database because of: " + exc.getMessage(), exc);
        }
    }

    public static boolean isUserEmailFree(String email) {
        try {
            return (Boolean) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_EMAIL.getQuery(), ResultHandlerFactory.HANDLER.get(ResultTemplate.EMAIL), email);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get email from database because of: " + exc.getMessage(), exc);
        }
    }

    public static void changeUserLanguage(int userId, String language) {
        try (Connection connection = DBConnectionsPool.getConnection()) {
            DAO_FACTORY.update(connection, Queries.UPDATE_USER_LANGUAGE.getQuery(), language, userId);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't set language to database for user because of: " + exc.getMessage(), exc);
        }
    }

    public static  void editUser(UserEditingForm form, List<String> edits) {

        Connection connection = null;
        try {
            connection = DBConnectionsPool.getConnection();
            connection.setAutoCommit(false);
            for (String edit : edits) {
                switch (edit) {
                    case "firstName":
                        DAO_FACTORY.update(connection, Queries.UPDATE_USER_FIST_NAME.getQuery(),
                                form.getFirstName(), form.getId());
                        break;
                    case "lastName":
                        DAO_FACTORY.update(connection, Queries.UPDATE_USER_LAST_NAME.getQuery(),
                                form.getLastName(), form.getId());
                        break;
                    case "email":
                        DAO_FACTORY.update(connection, Queries.UPDATE_USER_EMAIL.getQuery(),
                                form.getEmail(), form.getId());
                        break;
                    case "role":
                        DAO_FACTORY.update(connection, Queries.UPDATE_USER_ROLE.getQuery(),
                                form.getRole().name(), form.getId());
                        break;
                    default:
                        throw new SQLException("Can't edit such user data");
                }
            }
            connection.commit();
        } catch (SQLException exc) {
            try {
                connection.rollback();
            } catch (SQLException rlb_exc) {
                throw new DataBaseInteractionException(
                        "Can't edit user's data and Can't rollback editing because of: " +
                                exc.getMessage() + rlb_exc.getMessage(), rlb_exc);
            }
            throw new DataBaseInteractionException("Can't edit user's data because of: " + exc.getMessage(), exc);
        }finally {
            try {
                connection.close();
            } catch (SQLException cl_exc) {
                throw new DataBaseInteractionException("Can't close connection because of: " + cl_exc.getMessage(), cl_exc);
            }
        }
    }

    public static void deleteUser(int userId) {
        try {
            DAO_FACTORY.delete(DBConnectionsPool.getConnection(), Queries.DELETE_USER.getQuery(), userId);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't delete user from database because of: " + exc.getMessage(), exc);
        }
    }
}