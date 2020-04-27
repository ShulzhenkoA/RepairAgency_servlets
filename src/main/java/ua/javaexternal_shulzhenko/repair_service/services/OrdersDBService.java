package ua.javaexternal_shulzhenko.repair_service.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.javaexternal_shulzhenko.repair_service.dao.UniversalDAOFactory;
import ua.javaexternal_shulzhenko.repair_service.dao.Queries;
import ua.javaexternal_shulzhenko.repair_service.dao.result_handler.ResultTemplate;
import ua.javaexternal_shulzhenko.repair_service.dao.result_handler.ResultHandlerFactory;
import ua.javaexternal_shulzhenko.repair_service.exceptions.DataBaseInteractionException;
import ua.javaexternal_shulzhenko.repair_service.models.Order;
import ua.javaexternal_shulzhenko.repair_service.utils.DBConnectionsPool;

import java.sql.SQLException;
import java.util.LinkedList;

public class OrdersDBService {

    private static final UniversalDAOFactory DAO_FACTORY = UniversalDAOFactory.getDaoFactory();

    public static void addOrder(Order order){
        LinkedList<Object> orderFields = new LinkedList<>();
        extractOrderFields(order, orderFields);
        try {
            DAO_FACTORY.insert(DBConnectionsPool.getConnection(),
                    Queries.INSERT_ORDER.getQuery(), orderFields.toArray());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't add order to database because of: " + exc.getMessage(), exc);
        }
    }

    private static void extractOrderFields(Order order, LinkedList<Object> objects) {
        objects.add(order.getOrderDate());
        objects.add(order.getOrderContent());
        objects.add(order.getOrderPrice());
        objects.add(order.getOrderStatus().name());
    }

    public static Order getOrder() {
        try {
            return (Order) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_ORDER.getQuery(), ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDER));
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get order from database because of: " + exc.getMessage(), exc);
        }
    }
}
