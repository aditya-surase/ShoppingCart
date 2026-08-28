package com.shopping.shoppingcart.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.shopping.shoppingcart.entity.Order;
import com.shopping.shoppingcart.entity.Payment;
import com.shopping.shoppingcart.repository.OrderRepository;
import com.shopping.shoppingcart.repository.PaymentRepository;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          OrderRepository orderRepository) {

        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public Payment makePayment(Long orderId, String paymentMethod) {

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return null;
        }

        if (!order.getStatus().equals("PENDING")) {
            return null;
        }

        Payment payment = new Payment();

        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        // Payment successful → confirm order
        order.setStatus("CONFIRMED");
        orderRepository.save(order);

        return savedPayment;
    }
}