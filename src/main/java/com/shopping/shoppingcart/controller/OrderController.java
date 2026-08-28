package com.shopping.shoppingcart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.shoppingcart.dto.OrderRequestResponse;
import com.shopping.shoppingcart.dto.OrderResponse;
import com.shopping.shoppingcart.entity.Order;
import com.shopping.shoppingcart.service.OrderService;

@RestController
@RequestMapping("/orders")
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Checkout
    @PostMapping("/checkout/{userId}")
    public OrderResponse checkout(@PathVariable Long userId) {

        Order order = orderService.checkout(userId);

        if (order == null) {
            return null;
        }

        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getStatus()
        );
    }

    // Get user's orders
    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {

        return orderService.getUserOrders(userId);
    }

    // Get order by ID
    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId) {

        return orderService.getOrderById(orderId);
    }
    
    
    @PostMapping
    public OrderResponse placeOrder(
            @RequestBody OrderRequestResponse request) {

        Order order = orderService.placeOrder(
                request.getUserId(),
                request.getItems()
        );

        if (order == null) {
            return null;
        }

        return new OrderResponse(

                order.getId(),

                order.getUser().getId(),

                order.getOrderDate(),

                order.getTotalAmount(),

                order.getStatus()

        );
    }
}