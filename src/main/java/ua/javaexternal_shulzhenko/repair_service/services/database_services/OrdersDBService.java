package ua.javaexternal_shulzhenko.repair_service.services.database_services;

import ua.javaexternal_shulzhenko.repair_service.models.order.OrderStatus;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.dao.Queries;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.dao.UniversalDAOFactory;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.result_handler.ResultTemplate;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.result_handler.ResultHandlerFactory;
import ua.javaexternal_shulzhenko.repair_service.exceptions.DataBaseInteractionException;
import ua.javaexternal_shulzhenko.repair_service.models.order.Order;
import ua.javaexternal_shulzhenko.repair_service.services.database_services.connection.DBConnectionsPool;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OrdersDBService {

    private static final UniversalDAOFactory DAO_FACTORY = UniversalDAOFactory.getDaoFactory();

    public static void addOrder(Order order) {
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
        objects.add(order.getCustomer().getId());
        objects.add(order.getDate());
        objects.add(order.getCarBrand());
        objects.add(order.getCarModel());
        objects.add(order.getCarYearManufacture());
        objects.add(order.getRepairType().name());
        objects.add(order.getRepairDescription());
        objects.add(order.getStatus().name());
    }

    public static Order getLastOrderForRegUser(int userId) {
        try {
            return (Order) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.SELECT_LAST_USER_ORDER.getQuery(), ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDER), userId);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get order from database because of: " + exc.getMessage(), exc);
        }
    }

    public static List<Order> getOrdersByOffsetAmountStatus(User user, int offset, int amount, OrderStatus status) {
        try {
            switch (user.getRole()) {
                case CUSTOMER:
                    return (List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.CUSTOMER_SELECT_ORDERS_BY_OFFSET_AMOUNT_STATUS.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                            user.getId(), status.name(), offset, amount);
                case MASTER:
                    return (List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.MASTER_SELECT_ORDERS_BY_OFFSET_AMOUNT_STATUS.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                            user.getId(), status.name(), offset, amount);
                case MANAGER:
                    return (List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_STATUS.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                            status.name(), offset, amount);
                default:
                    throw new DataBaseInteractionException("Can't get orders from database for this user role.");
            }
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders from database because of: " + exc.getMessage(), exc);
        }
    }

    public static List<Order> getOrdersByOffsetAmountExcludeStatus(User user, int offset, int amount, OrderStatus status) {
        try {
            switch (user.getRole()) {
                case CUSTOMER:
                    return (List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.CUSTOMER_SELECT_ORDERS_BY_OFFSET_AMOUNT_EXCLUDE_STATUS.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                            user.getId(), status.name(), offset, amount);
                case MASTER:
                    return (List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.MASTER_SELECT_ORDERS_BY_OFFSET_AMOUNT_EXCLUDE_STATUS.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                            user.getId(), status.name(), offset, amount);
                case MANAGER:
                    return (List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_EXCLUDE_STATUS.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                            status.name(), offset, amount);
                default:
                    throw new DataBaseInteractionException("Can't get orders from database for this user role.");
            }
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders from database because of: " + exc.getMessage(), exc);
        }
    }

    public static int getOrdersAmountByStatus(User user, OrderStatus status) {
        try {
            switch (user.getRole()) {
                case CUSTOMER:
                    return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.CUSTOMER_SELECT_ORDERS_AMOUNT_BY_STATUS.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                            user.getId(), status.name());
                case MASTER:
                    return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.MASTER_SELECT_ORDERS_AMOUNT_BY_STATUS.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                            user.getId(), status.name());
                case MANAGER:
                    return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.MANAGER_SELECT_ORDERS_AMOUNT_BY_STATUS.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                            status.name());
                default:
                    throw new DataBaseInteractionException("Can't get orders amount from database for this user role.");
            }
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders amount from database because of: " + exc.getMessage(), exc);
        }
    }

    public static int getOrdersAmountByExcludeStatus(User user, OrderStatus status) {
        try {
            switch (user.getRole()) {
                case CUSTOMER:
                    return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.CUSTOMER_SELECT_ORDERS_AMOUNT_BY_EXCLUDE_STATUS.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                            user.getId(), status.name());
                case MASTER:
                    return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.MASTER_SELECT_ORDERS_AMOUNT_BY_EXCLUDE_STATUS.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                            user.getId(), status.name());
                case MANAGER:
                    return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                            Queries.MANAGER_SELECT_ORDERS_AMOUNT_BY_EXCLUDE_STATUS.getQuery(),
                            ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                            status.name());
                default:
                    throw new DataBaseInteractionException("Can't get orders amount from database for this user role.");
            }
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders amount from database because of: " + exc.getMessage(), exc);
        }
    }

    public static List<Order> getOrdersByAmountOffsetTwoStatuses(int offset, int amount, OrderStatus... statuses) {
        try {
            return (List<Order>) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_STATUSES.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.ORDERS),
                    statuses[0].name(), statuses[1].name(), offset, amount);
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders from database of such statuses because of: " + exc.getMessage(), exc);
        }
    }

    public static int getOrdersAmountByTwoStatuses(OrderStatus... statuses) {
        try {
            return (Integer) DAO_FACTORY.select(DBConnectionsPool.getConnection(),
                    Queries.MANAGER_SELECT_ORDERS_AMOUNT_BY_TWO_STATUSES.getQuery(),
                    ResultHandlerFactory.HANDLER.get(ResultTemplate.AMOUNT),
                    statuses[0].name(), statuses[1].name());
        } catch (SQLException exc) {
            throw new DataBaseInteractionException("Can't get orders amount from database because of: " + exc.getMessage(), exc);
        }
    }


}
