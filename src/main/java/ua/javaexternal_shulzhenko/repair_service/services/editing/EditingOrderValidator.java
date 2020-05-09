package ua.javaexternal_shulzhenko.repair_service.services.editing;

import ua.javaexternal_shulzhenko.repair_service.models.forms.OrderEditingForm;
import ua.javaexternal_shulzhenko.repair_service.models.order.OrderStatus;

import java.util.Set;

public interface EditingOrderValidator {

    static void checkIfNeedMasterForThisStatus(OrderEditingForm form, Set<String> inconsistencies){
        String masterID = form.getMasterID();
        OrderStatus status = form.getStatus();
        if(!status.equals(OrderStatus.REJECTED) && masterID.equals("0")){
            inconsistencies.add("master");
        }
    }

    static void checkIfNeedPreviousPrice(OrderEditingForm form, Set<String> inconsistencies){
        if(!inconsistencies.contains("price")){
            Double price = Double.parseDouble(form.getPrice());
            OrderStatus status = form.getStatus();
            if(!status.equals(OrderStatus.REJECTED) && price <= 0){
                inconsistencies.add("price");
            }
        }
    }
}
