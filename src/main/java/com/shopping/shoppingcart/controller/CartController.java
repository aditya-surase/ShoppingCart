package com.shopping.shoppingcart.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.shoppingcart.entity.CartItem;
import com.shopping.shoppingcart.service.CartService;

@RestController
@RequestMapping("/cart")
@CrossOrigin("*")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Add product to cart
    @PostMapping("/add")
    public CartItem addToCart(
            @RequestParam Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity) {

        return cartService.addToCart(userId, productId, quantity);
    }

    // Get user's cart
    @GetMapping("/{userId}")
    public List<CartItem> getCart(@PathVariable Long userId) {

        return cartService.getCart(userId);
    }
    
    @GetMapping("/total/{userId}")
    public double getCartTotal(@PathVariable Long userId) {

        return cartService.getCartTotal(userId);
    }

    // Remove item from cart
    @DeleteMapping("/remove/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId) {

        boolean removed = cartService.removeFromCart(cartItemId);

        if (removed) {
            return "Cart item removed successfully";
        }

        return "Cart item not found";
    }
}