package com.shopping.shoppingcart.dto;

import java.time.LocalDateTime;

public class AdminOrderResponse {

    private Long orderId;
    private Long userId;
    private Double totalAmount;
    private String status;
    private LocalDateTime orderDate;

    public AdminOrderResponse() {
    }

    public AdminOrderResponse(Long orderId,
                              Long userId,
                              Double totalAmount,
                              String status,
                              LocalDateTime orderDate) {

        this.orderId = orderId;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}