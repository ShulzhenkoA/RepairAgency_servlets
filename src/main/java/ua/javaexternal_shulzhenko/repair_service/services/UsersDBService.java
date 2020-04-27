package ua.javaexternal_shulzhenko.repair_service.services;

import ua.javaexternal_shulzhenko.repair_service.dao.UniversalDAOFactory;
import ua.javaexternal_shulzhenko.repair_service.dao.Queries;
import ua.javaexternal_shulzhenko.repair_service.dao.result_handler.ResultTemplate;
import ua.javaexternal_shulzhenko.repair_service.dao.result_handler.ResultHandlerFactory;
import ua.javaexternal_shulzhenko.repair_service.exceptions.DataBaseInteractionException;
import ua.javaexternal_shulzhenko.repair_service.models.User;
import ua.javaexternal_shulzhenko.repair_service.services.validation.registration_form.RegistrationForm;
import ua.javaexternal_shulzhenko.repair_service.utils.DBConnectionsPool;

import java.sql.SQLException;
import java.util.LinkedList;


public class UsersDBService {

    private static UniversalDAOFactory DAO_FACTORY = UniversalDAOFactory.getDaoFactory();

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
        formFields.add(registrationForm.getPassword());
        formFields.add("English");                                 /////////////////////////////////////////////////////////////////////////////// Refactor here!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        formFields.add(registrationForm.getRole().name());
    }

    public static User getUserByEmail(String email) {
        try {
            return (User) DAO_FACTORY.select(DBConnectionsPool.getConnection(), Queries.SELECT_USER_BY_EMAIL.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.USER), email);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get user from database because of: " + exc.getMessage(), exc);
        }
    }

    public static boolean isUserEmailFree(String email){
        try {
            return (Boolean) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_EMAIL.getQuery(), ResultHandlerFactory.HANDLER.get(ResultTemplate.EMAIL), email);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get email from database because of: " + exc.getMessage(), exc);
        }
    }
}
