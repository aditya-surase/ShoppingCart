package com.shopping.shoppingcart.dto;

import java.util.List;

public class OrderRequestResponse {

    private Long userId;

    private List<OrderItemRequestResponse> items;

    public OrderRequestResponse() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemRequestResponse> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequestResponse> items) {
        this.items = items;
    }
}