package ua.javaexternal_shulzhenko.repair_service.models;

import java.time.LocalDateTime;

public class Order {

    private int id;
    private String customerName;
    private LocalDateTime orderDate;
    private String orderContent;
    private double orderPrice;
    private String masterName;
    private LocalDateTime repairCompletionDate;
    private OrderStatus orderStatus;
    private String orderManagerInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderContent() {
        return orderContent;
    }

    public void setOrderContent(String orderContent) {
        this.orderContent = orderContent;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getMasterName() {
        return masterName;
    }

    public void setMasterName(String masterName) {
        this.masterName = masterName;
    }

    public LocalDateTime getRepairCompletionDate() {
        return repairCompletionDate;
    }

    public void setRepairCompletionDate(LocalDateTime repairCompletionDate) {
        this.repairCompletionDate = repairCompletionDate;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderManagerInfo() {
        return orderManagerInfo;
    }

    public void setOrderManagerInfo(String orderManagerInfo) {
        this.orderManagerInfo = orderManagerInfo;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerName='" + customerName + '\'' +
                ", orderDate=" + orderDate +
                ", orderContent='" + orderContent + '\'' +
                ", orderPrice=" + orderPrice +
                ", masterName='" + masterName + '\'' +
                ", repairCompletionDate=" + repairCompletionDate +
                ", orderStatus=" + orderStatus +
                ", orderManagerInfo='" + orderManagerInfo + '\'' +
                '}';
    }
}
