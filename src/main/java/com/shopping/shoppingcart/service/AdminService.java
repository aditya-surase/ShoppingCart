package com.shopping.shoppingcart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.shoppingcart.dto.AdminUserResponse;
import com.shopping.shoppingcart.entity.Order;
import com.shopping.shoppingcart.entity.User;
import com.shopping.shoppingcart.repository.OrderRepository;
import com.shopping.shoppingcart.repository.UserRepository;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public AdminService(
            UserRepository userRepository,
            OrderRepository orderRepository) {

        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    // ===============================
    // ADMIN LOGIN
    // ===============================

    public User login(String email, String password) {

        User user =
                userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return null;
        }

        if (!user.getPassword().equals(password)) {
            return null;
        }

        if (!"ADMIN".equals(user.getRole())) {
            return null;
        }

        return user;
    }

    // ===============================
    // UPDATE ORDER STATUS
    // ===============================

    public Order updateOrderStatus(Long orderId, String status) {

        System.out.println("Order ID: " + orderId);
        System.out.println("Status: " + status);

        Order order =
                orderRepository.findById(orderId).orElse(null);

        if (order == null) {

            System.out.println("Order not found!");

            return null;
        }

        System.out.println(
                "Old Status: " + order.getStatus()
        );

        order.setStatus(status);

        Order updatedOrder =
                orderRepository.save(order);

        System.out.println(
                "New Status: " + updatedOrder.getStatus()
        );

        return updatedOrder;
    }

    // ===============================
    // VIEW ALL USERS
    // ===============================

    public List<AdminUserResponse> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> new AdminUserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getAddress(),
                        user.getRole()
                ))
                .toList();
    }

    // ===============================
    // VIEW ALL ORDERS
    // ===============================

    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }
}