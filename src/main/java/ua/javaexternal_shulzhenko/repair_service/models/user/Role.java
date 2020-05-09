package ua.javaexternal_shulzhenko.repair_service.models.user;

import java.util.Arrays;
import java.util.List;

public enum Role {
    ADMIN(Arrays.asList("/admin_home", "/man_mas_registration", "/edit_user","/delete_user")),
    MANAGER(Arrays.asList("/personal_page")),
    MASTER(Arrays.asList("/master_home", "/master_completed_orders", "/edit_status")),
    CUSTOMER(Arrays.asList("/customer_home", "/customer_order_history","/create_order")),
    UNKNOWN(Arrays.asList("/login", "/registration"));

    private List<String> urls;

    Role(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getUrls() {
        return urls;
    }
}
