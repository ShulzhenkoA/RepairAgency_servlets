package ua.javaexternal_shulzhenko.repair_service.dao;

public enum Queries {

    INSERT_USER("INSERT INTO repair_agency.users(first_name, last_name, email, password, language, role) " +
            "VALUES(?, ?, ?, ?, ?, ?)"),

    SELECT_USER_BY_NAME("select * from users where first_name = ? and last_name = ?"),
    SELECT_USER_BY_EMAIL("select * from users where email = ?"),
    SELECT_USER_BY_ROLE("select * from users where role = ?"),

    SELECT_ORDER("SELECT orders.id, concat(customer.first_name, ' ', customer.last_name) customer_name, " +
            "orders.order_date, orders.order_content, orders.order_price, " +
            "concat(master.first_name, ' ', master.last_name) master_name, " +
            "orders.repair_completion_date, orders.order_status, orders.order_manager_info " +
            "FROM orders " +
            "JOIN users customer ON orders.customer_id = customer.id " +
            "LEFT JOIN users master ON orders.master_id = master.id"),

    INSERT_ORDER("INSERT INTO repair_agency.orders(customer_id, order_date, order_content, order_price, order_status) " +
            "values(1, ?, ?, ?, ?)");



    private String query;

    Queries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
