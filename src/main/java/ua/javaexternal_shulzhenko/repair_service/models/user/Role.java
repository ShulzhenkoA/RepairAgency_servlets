package ua.javaexternal_shulzhenko.repair_service.models.user;

import ua.javaexternal_shulzhenko.repair_service.constants.CRAPaths;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN(Arrays.asList(CRAPaths.ADMIN_HOME, CRAPaths.MAN_MAS_REGISTRATION, CRAPaths.EDIT_USER,
            CRAPaths.DELETE_USER, CRAPaths.LOGOUT)),
    MANAGER(Arrays.asList(CRAPaths.MANAGER_HOME, CRAPaths.ACTIVE_ORDERS, CRAPaths.EDIT_ORDER,
            CRAPaths.ORDER_HISTORY, CRAPaths.CUSTOMERS, CRAPaths.MASTERS, CRAPaths.LOGOUT)),
    MASTER(Arrays.asList(CRAPaths.MASTER_HOME, CRAPaths.MASTER_COMPLETED_ORDERS, CRAPaths.EDIT_STATUS, CRAPaths.LOGOUT)),
    CUSTOMER(Arrays.asList(CRAPaths.CUSTOMER_HOME, CRAPaths.CREATE_ORDER, CRAPaths.CUSTOMER_ORDER_HISTORY, CRAPaths.LOGOUT)),
    UNKNOWN(Arrays.asList(CRAPaths.LOGIN, CRAPaths.REGISTRATION));

    private List<String> urls;

    Role(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }
}
