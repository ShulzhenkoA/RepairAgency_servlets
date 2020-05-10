package ua.javaexternal_shulzhenko.repair_service.models.forms;

import ua.javaexternal_shulzhenko.repair_service.constants.Parameters;
import ua.javaexternal_shulzhenko.repair_service.models.order.OrderStatus;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.MustConform;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.NotEmpty;
import ua.javaexternal_shulzhenko.repair_service.services.validation.regex.Regex;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public class OrderEditingForm implements Form{

    private final int id;

    @MustConform(Regex.PRICE)
    private final String price;
    private final String masterID;
    private final OrderStatus status;
    @NotEmpty
    private final String managerComment;

    public OrderEditingForm(HttpServletRequest req) {
        id = Integer.parseInt(req.getParameter(Parameters.EDITING_ORDER_ID));
        price = req.getParameter(Parameters.PRICE);
        masterID = req.getParameter(Parameters.MASTER_ID);
        status = OrderStatus.valueOf(req.getParameter(Parameters.STATUS));
        managerComment = req.getParameter(Parameters.MANAGER_COMMENT);
    }

    public int getId() {
        return id;
    }

    public String getPrice() {
        return price;
    }

    public String  getMasterID() {
        return masterID;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getManagerComment() {
        return managerComment;
    }

}
