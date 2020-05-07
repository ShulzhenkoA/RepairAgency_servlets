package ua.javaexternal_shulzhenko.repair_service.models.pagination;

public enum PaginationConstants {
    ORDERS_FOR_PAGE(5),
    USERS_FOR_PAGE(10);
    private int amount;

    PaginationConstants(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
