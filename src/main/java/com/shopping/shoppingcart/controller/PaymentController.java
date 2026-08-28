package com.shopping.shoppingcart.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.shoppingcart.entity.Payment;
import com.shopping.shoppingcart.service.PaymentService;

@RestController
@RequestMapping("/payments")
@CrossOrigin("*")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public Payment makePayment(
            @RequestParam Long orderId,
            @RequestParam String paymentMethod) {

        return paymentService.makePayment(orderId, paymentMethod);
    }
}