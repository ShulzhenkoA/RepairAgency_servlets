package ua.javaexternal_shulzhenko.repair_service.dao.result_handler;

import ua.javaexternal_shulzhenko.repair_service.exceptions.ValidationException;
import ua.javaexternal_shulzhenko.repair_service.models.Order;
import ua.javaexternal_shulzhenko.repair_service.models.OrderStatus;
import ua.javaexternal_shulzhenko.repair_service.models.Role;
import ua.javaexternal_shulzhenko.repair_service.models.User;

import java.sql.Timestamp;
import java.util.HashMap;

public class ResultHandlerFactory {

    public final static HashMap<ResultTemplate, ResultHandler<?>> HANDLER = new HashMap();

    static {
        HANDLER.put(ResultTemplate.USER, resultSet -> {

           if(resultSet.next()){
               User user = new User();
               user.setId(resultSet.getInt("id"));
               user.setFirstName(resultSet.getString("first_name"));
               user.setLastName(resultSet.getString("last_name"));
               user.setEmail(resultSet.getString("email"));
               user.setPassword(resultSet.getInt("password"));
               user.setLanguage(resultSet.getString("language"));
               user.setRole(Role.valueOf(resultSet.getString("role")));
               return user;
           }else{
               throw new ValidationException("User with this email don't exist");
           }
        });

        HANDLER.put(ResultTemplate.ORDER, resultSet -> {
           Order order = new Order();
           if(resultSet.next()){
               order.setId(resultSet.getInt("id"));
               order.setCustomerName(resultSet.getString("customer_name"));
               order.setOrderDate(resultSet.getTimestamp("order_date").toLocalDateTime());
               order.setOrderContent(resultSet.getString("order_content"));
               order.setOrderPrice(resultSet.getDouble("order_price"));
               order.setMasterName(resultSet.getString("master_name"));

               Timestamp repairCompletion = resultSet.getTimestamp("repair_completion_date");
               if(repairCompletion != null){
                   order.setRepairCompletionDate(repairCompletion.toLocalDateTime());
               }
               order.setOrderStatus(OrderStatus.valueOf(resultSet.getString("order_status")));
               order.setOrderManagerInfo(resultSet.getString("order_manager_info"));
           }
           return order;
        });
    }



    private ResultHandlerFactory(){};
}
