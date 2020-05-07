package ua.javaexternal_shulzhenko.repair_service.services.database_services.result_handler;

import ua.javaexternal_shulzhenko.repair_service.models.order.Order;
import ua.javaexternal_shulzhenko.repair_service.models.order.OrderStatus;
import ua.javaexternal_shulzhenko.repair_service.models.order.RepairType;
import ua.javaexternal_shulzhenko.repair_service.models.user.Role;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ResultHandlerFactory {

    public final static HashMap<ResultTemplate, ResultHandler<?>> HANDLER = new HashMap();

    private ResultHandlerFactory() {
    }

    static {

        HANDLER.put(ResultTemplate.USER, resultSet -> {
            if (resultSet.next()) {
                User user = new User.UserBuilder().
                        setId(resultSet.getInt("id")).
                        setFirstName(resultSet.getString("first_name")).
                        setLastName(resultSet.getString("last_name")).
                        setEmail(resultSet.getString("email")).
                        setPassword(resultSet.getInt("password")).
                        setLanguage(resultSet.getString("language")).
                        setRole(Role.valueOf(resultSet.getString("role"))).build();
                return user;
            } else {
                return null;                                    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Handle exception!!!!!!!!!!!!!!!!!!!!!
            }
        });

        HANDLER.put(ResultTemplate.USERS, resultSet -> {
            List<User> list = new LinkedList<>();
            User user;
            do {
                user = (User) HANDLER.get(ResultTemplate.USER).handleResultSet(resultSet);
            } while (user != null && list.add(user));
            return list;
        });

        HANDLER.put(ResultTemplate.ORDER, resultSet -> {
            if (resultSet.next()) {
                Order order = new Order.OrderBuilder().
                        setId(resultSet.getInt("id")).
                        setCustomer(new User.UserBuilder().
                                setId(resultSet.getInt("customer_id")).
                                setFirstName(resultSet.getString("customer_f_name")).
                                setLastName(resultSet.getString("customer_l_name")).
                                setEmail(resultSet.getString("email")).
                                build()).
                        setDate(resultSet.getTimestamp("creation_date").toLocalDateTime()).
                        setCarBrand(resultSet.getString("brand")).
                        setCarModel(resultSet.getString("model")).
                        setCarYearManufacture(resultSet.getString("year")).
                        setRepairType(RepairType.valueOf(resultSet.getString("repair_type"))).
                        setRepairDescription(resultSet.getString("repair_description")).
                        setPrice(resultSet.getDouble("price")).
                        setMaster(new User.UserBuilder().
                                setId(resultSet.getInt("master_id")).
                                setFirstName(resultSet.getString("master_f_name")).
                                setLastName(resultSet.getString("master_l_name")).
                                build()).
                        setStatus(OrderStatus.valueOf(resultSet.getString("status"))).
                        setManagerComment(resultSet.getString("manager_comment")).
                        build();
                Timestamp repairCompletion = resultSet.getTimestamp("repair_completion_date");
                if (repairCompletion != null) {
                    order.setRepairCompletionDate(repairCompletion.toLocalDateTime());
                }
                return order;
            } else {
                return null;                                    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Handle exception!!!!!!!!!!!!!!!!!!!!!
            }
        });

        HANDLER.put(ResultTemplate.ORDERS, resultSet -> {
            List<Order> list = new LinkedList<>();
            Order order;
            do {
                order = (Order) HANDLER.get(ResultTemplate.ORDER).handleResultSet(resultSet);
            } while (order != null && list.add(order));
            return list;
        });

        HANDLER.put(ResultTemplate.EMAIL, resultSet -> !resultSet.next());

        HANDLER.put(ResultTemplate.AMOUNT, resultSet -> {
            if (resultSet.next()) {
                return resultSet.getInt("amount");
            } else {
                return null;                            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! Handle exception!!!!!!!!!!!!!!!!!!!!!
            }
        });
    }
}
