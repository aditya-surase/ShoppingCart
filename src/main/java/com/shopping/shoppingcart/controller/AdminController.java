package com.shopping.shoppingcart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.shopping.shoppingcart.dto.AdminUserResponse;
import com.shopping.shoppingcart.entity.Order;
import com.shopping.shoppingcart.entity.User;
import com.shopping.shoppingcart.service.AdminService;

import org.springframework.web.bind.annotation.PutMapping;






@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // View all users
    @GetMapping("/users")
    public List<AdminUserResponse> getAllUsers() {

        return adminService.getAllUsers();
    }

    // View all orders
    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return adminService.getAllOrders();
    }
    
    
    @PutMapping("/orders/{orderId}/status")
    public Order updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam String status) {

        return adminService.updateOrderStatus(orderId, status);
    }
    
    

    // Admin login
    @PostMapping("/login")
    public String login(
            @RequestParam String email,
            @RequestParam String password) {

        User user = adminService.login(email, password);

        if (user == null) {
            return "Invalid admin credentials";
        }

        return "Admin login successful";
    }
}