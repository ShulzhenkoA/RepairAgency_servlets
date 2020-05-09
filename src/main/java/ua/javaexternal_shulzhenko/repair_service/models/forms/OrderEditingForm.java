package ua.javaexternal_shulzhenko.repair_service.models.forms;

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
        id = Integer.parseInt(req.getParameter("editing_order_id"));
        price = req.getParameter("price");
        masterID = req.getParameter("masterID");
        status = OrderStatus.valueOf(req.getParameter("status"));
        managerComment = req.getParameter("managerComment");
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
