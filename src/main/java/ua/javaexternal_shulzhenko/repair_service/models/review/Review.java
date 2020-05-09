package ua.javaexternal_shulzhenko.repair_service.models.review;

import ua.javaexternal_shulzhenko.repair_service.models.user.User;

import java.time.LocalDateTime;

public class Review {

    private int id;
    private User customer;
    private String content;
    private LocalDateTime dateTime;

    public int getId() {
        return id;
    }

    public User getCustomer() {
        return customer;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
