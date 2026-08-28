package com.shopping.shoppingcart.dto;

public class OrderItemRequestResponse {

    private Long productId;

    private int quantity;

    public OrderItemRequestResponse() {
    }

    public OrderItemRequestResponse(Long productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}