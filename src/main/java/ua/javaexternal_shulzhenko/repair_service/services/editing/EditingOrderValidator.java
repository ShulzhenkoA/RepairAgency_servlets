package ua.javaexternal_shulzhenko.repair_service.services.editing;

import ua.javaexternal_shulzhenko.repair_service.constants.CommonConstants;
import ua.javaexternal_shulzhenko.repair_service.models.forms.OrderEditingForm;
import ua.javaexternal_shulzhenko.repair_service.constants.OrderStatus;

import java.util.Set;

public interface EditingOrderValidator {

    static void checkIfNeedMasterForThisStatus(OrderEditingForm form, Set<String> inconsistencies){
        String masterID = form.getMasterID();
        OrderStatus status = form.getStatus();
        if(!status.equals(OrderStatus.REJECTED) && masterID.equals(CommonConstants.ZERO)){
            inconsistencies.add(CommonConstants.MASTER);
        }
    }

    static void checkIfNeedPreviousPrice(OrderEditingForm form, Set<String> inconsistencies){
        if(!inconsistencies.contains(CommonConstants.PRICE)){
            Double price = Double.parseDouble(form.getPrice());
            OrderStatus status = form.getStatus();
            if(!status.equals(OrderStatus.REJECTED) && price <= 0){
                inconsistencies.add(CommonConstants.PRICE);
            }
        }
    }
}
