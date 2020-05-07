package ua.javaexternal_shulzhenko.repair_service.models.forms;

import ua.javaexternal_shulzhenko.repair_service.models.order.RepairType;
import ua.javaexternal_shulzhenko.repair_service.models.user.User;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.MustConform;
import ua.javaexternal_shulzhenko.repair_service.services.validation.annotations.NotEmpty;
import ua.javaexternal_shulzhenko.repair_service.services.validation.regex.Regex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class OrderForm implements Form {

    private final User user;

    @MustConform(Regex.CAR_BRAND)
    private final String carBrand;

    @MustConform(Regex.CAR_MODEL)
    private final String carModel;

    @MustConform(Regex.CAR_YEAR)
    private final String carYear;

    @NotEmpty
    private final RepairType repairType;

    @NotEmpty
    private final String repairDescription;

    public OrderForm(HttpServletRequest req) {
        user = extractUser(req);
        carBrand = req.getParameter("car_brand");
        carModel = req.getParameter("car_model");
        carYear = req.getParameter("car_year");
        repairDescription = req.getParameter("repair_description");
        repairType = extractRepairType(req);
    }

    private User extractUser(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (User) session.getAttribute("user");
    }

    private RepairType extractRepairType(HttpServletRequest req) {
        String repairType = req.getParameter("repair_type");
        if (!repairType.equals("Repair type")) {
            return RepairType.valueOf(req.getParameter("repair_type"));
        }
        return null;
    }

    public User getUser() {
        return user;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getCarYear() {
        return carYear;
    }

    public RepairType getRepairType() {
        return repairType;
    }

    public String getRepairDescription() {
        return repairDescription;
    }
}
