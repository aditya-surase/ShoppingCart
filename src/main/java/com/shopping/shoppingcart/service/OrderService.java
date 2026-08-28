package com.shopping.shoppingcart.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.shoppingcart.entity.Cart;
import com.shopping.shoppingcart.entity.CartItem;
import com.shopping.shoppingcart.entity.Order;
import com.shopping.shoppingcart.entity.OrderItem;
import com.shopping.shoppingcart.entity.Product;
import com.shopping.shoppingcart.entity.User;
import com.shopping.shoppingcart.repository.CartItemRepository;
import com.shopping.shoppingcart.repository.CartRepository;
import com.shopping.shoppingcart.repository.OrderItemRepository;
import com.shopping.shoppingcart.repository.OrderRepository;
import com.shopping.shoppingcart.repository.ProductRepository;
import com.shopping.shoppingcart.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository,
            CartRepository cartRepository,
            CartItemRepository cartItemRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    // Checkout
    @Transactional
    public Order checkout(Long userId) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return null;
        }

        Cart cart = cartRepository.findByUser(user).orElse(null);

        if (cart == null) {
            return null;
        }

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            return null;
        }

        double totalAmount = 0;

        // Calculate total
        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            int quantity = cartItem.getQuantity();

            if (product.getQuantity() < quantity) {
                return null;
            }

            totalAmount =
                    totalAmount + (product.getPrice() * quantity);
        }

        // Create Order
        Order order = new Order();

        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setTotalAmount(totalAmount);
        order.setStatus("PENDING");

        order = orderRepository.save(order);

        // Create Order Items
        for (CartItem cartItem : cartItems) {

            Product product = cartItem.getProduct();

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());

            orderItemRepository.save(orderItem);

            // Reduce stock
            product.setQuantity(
                    product.getQuantity() - cartItem.getQuantity()
            );

            productRepository.save(product);
        }

        // Clear cart
        for (CartItem cartItem : cartItems) {
            cartItemRepository.delete(cartItem);
        }

        return order;
    }

    // Get all orders of a user
    public List<Order> getUserOrders(Long userId) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return List.of();
        }

        return orderRepository.findByUser(user);
    }

    // Get order by ID
    public Order getOrderById(Long orderId) {

        return orderRepository.findById(orderId).orElse(null);
    }
    
    
    public Order updateOrderStatus(Long orderId, String status) {

        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null) {
            return null;
        }

        order.setStatus(status);

        return orderRepository.save(order);
    }
    
    @Transactional
    public Order placeOrder(
            Long userId,
            List<com.shopping.shoppingcart.dto.OrderItemRequestResponse> items) {

        // Find user
        User user = userRepository
                .findById(userId)
                .orElse(null);

        if (user == null) {
            return null;
        }

        if (items == null || items.isEmpty()) {
            return null;
        }

        double totalAmount = 0;

        // Check products and calculate total
        for (com.shopping.shoppingcart.dto.OrderItemRequestResponse item : items) {

            Product product = productRepository
                    .findById(item.getProductId())
                    .orElse(null);

            if (product == null) {
                return null;
            }

            if (item.getQuantity() <= 0) {
                return null;
            }

            if (!product.isActive()) {
                return null;
            }

            if (product.getQuantity() < item.getQuantity()) {
                return null;
            }

            totalAmount =
                    totalAmount +
                    (product.getPrice() * item.getQuantity());
        }

        // Create Order
        Order order = new Order();

        order.setUser(user);

        order.setOrderDate(LocalDateTime.now());

        order.setTotalAmount(totalAmount);

        order.setStatus("PENDING");

        order = orderRepository.save(order);


        // Create OrderItems and reduce stock
        for (com.shopping.shoppingcart.dto.OrderItemRequestResponse item : items) {

            Product product = productRepository
                    .findById(item.getProductId())
                    .orElse(null);

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);

            orderItem.setProduct(product);

            orderItem.setQuantity(item.getQuantity());

            orderItem.setPrice(product.getPrice());

            orderItemRepository.save(orderItem);


            // Reduce product stock
            product.setQuantity(
                    product.getQuantity() - item.getQuantity()
            );

            productRepository.save(product);
        }

        return order;
    }
}