package ua.javaexternal_shulzhenko.repair_service.services.database_services.dao;

public enum Queries {

    INSERT_USER("INSERT INTO repair_agency.users(first_name, last_name, email, password, language, role) " +
            "VALUES(?, ?, ?, ?, ?, ?)"),
    INSERT_ORDER("CALL add_order(?, ?, ?, ?, ?, ?, ?, ?)"),

    SELECT_USER_BY_ID("SELECT * FROM users WHERE id = ?"),
    SELECT_USER_BY_EMAIL("SELECT * FROM users WHERE email = ?"),
    SELECT_USERS_BY_ROLE("SELECT * FROM users WHERE role = ?"),
    SELECT_USERS_BY_ROLE_OFFSET_AMOUNT("SELECT * FROM users WHERE role = ? LIMIT ?, ?"),
    SELECT_USERS_AMOUNT_BY_ROLE("SELECT count(id) as amount FROM users WHERE role = ?"),

    SELECT_EMAIL("SELECT email FROM users WHERE email = ?"),

    SELECT_LAST_USER_ORDER(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
            "customer.last_name as customer_l_name, customer.email, cars.brand, cars.model, cars.year, " +
            "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
            "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
            "FROM orders " +
            "JOIN cars ON orders.car_id = cars.id " +
            "JOIN users as customer ON orders.customer_id = customer.id " +
            "LEFT JOIN users as master ON orders.master_id = master.id " +
            "WHERE orders.customer_id = ? ORDER BY orders.id DESC LIMIT 1"),
    CUSTOMER_SELECT_ORDERS_BY_OFFSET_AMOUNT_STATUS(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.model, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.customer_id = ? AND orders.status = ? ORDER BY orders.id DESC LIMIT ?, ?"),
    CUSTOMER_SELECT_ORDERS_BY_OFFSET_AMOUNT_EXCLUDE_STATUS(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.model, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.customer_id = ? AND orders.status != ? ORDER BY orders.id DESC LIMIT ?, ?"),
    CUSTOMER_SELECT_ORDERS_AMOUNT_BY_STATUS(
            "SELECT count(id) as amount FROM orders WHERE customer_id = ? AND status = ?"),
    CUSTOMER_SELECT_ORDERS_AMOUNT_BY_EXCLUDE_STATUS(
            "SELECT count(id) as amount FROM orders WHERE customer_id = ? AND status != ?"),
    MASTER_SELECT_ORDERS_BY_OFFSET_AMOUNT_STATUS(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.model, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.master_id = ? AND orders.status = ? ORDER BY orders.id DESC LIMIT ?, ?"),
    MASTER_SELECT_ORDERS_BY_OFFSET_AMOUNT_EXCLUDE_STATUS(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.model, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.master_id = ? AND orders.status != ? ORDER BY orders.id DESC LIMIT ?, ?"),
    MASTER_SELECT_ORDERS_AMOUNT_BY_STATUS(
            "SELECT count(id) as amount FROM orders WHERE master_id = ? AND status = ?"),
    MASTER_SELECT_ORDERS_AMOUNT_BY_EXCLUDE_STATUS(
            "SELECT count(id) as amount FROM orders WHERE master_id = ? AND status != ?"),
    MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_STATUS(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.model, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.status = ? ORDER BY orders.id DESC LIMIT ?, ?"),
    MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_EXCLUDE_STATUS(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.model, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.status != ? ORDER BY orders.id DESC LIMIT ?, ?"),
    MANAGER_SELECT_ORDERS_AMOUNT_BY_STATUS(
            "SELECT count(id) as amount FROM orders WHERE status = ?"),
    MANAGER_SELECT_ORDERS_AMOUNT_BY_EXCLUDE_STATUS(
            "SELECT count(id) as amount FROM orders WHERE status != ?"),
    MANAGER_SELECT_ORDERS_BY_OFFSET_AMOUNT_TWO_STATUSES(
            "SELECT orders.id, orders.creation_date, customer.id as customer_id, customer.first_name as customer_f_name, " +
                    "customer.last_name as customer_l_name, customer.email, cars.brand, cars.model, cars.year, " +
                    "orders.repair_type, orders.repair_description, orders.price, master.id as master_id, master.first_name as master_f_name, " +
                    "master.last_name as master_l_name, orders.repair_completion_date, orders.status, orders.manager_comment " +
                    "FROM orders " +
                    "JOIN cars ON orders.car_id = cars.id " +
                    "JOIN users as customer ON orders.customer_id = customer.id " +
                    "LEFT JOIN users as master ON orders.master_id = master.id " +
                    "WHERE orders.status = ? OR orders.status = ? ORDER BY orders.id DESC LIMIT ?, ?"),
    MANAGER_SELECT_ORDERS_AMOUNT_BY_TWO_STATUSES(
            "SELECT count(id) as amount FROM orders WHERE status IN (?, ?)"),

    UPDATE_USER_FIST_NAME("UPDATE users SET first_name = ? WHERE id = ?"),
    UPDATE_USER_LAST_NAME("UPDATE users SET last_name = ? WHERE id = ?"),
    UPDATE_USER_EMAIL("UPDATE users SET email = ? WHERE id = ?"),
    UPDATE_USER_ROLE("UPDATE users SET role = ? WHERE id = ?"),
    UPDATE_USER_LANGUAGE("UPDATE users SET language = ? WHERE id = ?"),

    DELETE_USER("DELETE FROM users WHERE id = ?"),

    ;

    private String query;

    Queries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}