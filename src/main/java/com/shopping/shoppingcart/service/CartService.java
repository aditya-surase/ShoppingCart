package com.shopping.shoppingcart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.shoppingcart.entity.Cart;
import com.shopping.shoppingcart.entity.CartItem;
import com.shopping.shoppingcart.entity.Product;
import com.shopping.shoppingcart.entity.User;
import com.shopping.shoppingcart.repository.CartItemRepository;
import com.shopping.shoppingcart.repository.CartRepository;
import com.shopping.shoppingcart.repository.ProductRepository;
import com.shopping.shoppingcart.repository.UserRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository) {

        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    // Add Product to Cart
    public CartItem addToCart(Long userId, Long productId, int quantity) {

        User user = userRepository.findById(userId).orElse(null);
        Product product = productRepository.findById(productId).orElse(null);

        if (user == null || product == null) {
            return null;
        }

        if (quantity <= 0) {
            return null;
        }

        if (product.getQuantity() < quantity) {
            return null;
        }

        Cart cart = cartRepository.findByUser(user).orElse(null);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        CartItem cartItem =
                cartItemRepository.findByCartAndProduct(cart, product)
                                  .orElse(null);

        if (cartItem != null) {

            int newQuantity = cartItem.getQuantity() + quantity;

            if (newQuantity > product.getQuantity()) {
                return null;
            }

            cartItem.setQuantity(newQuantity);

        } else {

            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }

        return cartItemRepository.save(cartItem);
    }
    //calculate the total price.
    public double getCartTotal(Long userId) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return 0;
        }

        Cart cart = cartRepository.findByUser(user).orElse(null);

        if (cart == null) {
            return 0;
        }

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        double total = 0;

        for (CartItem item : cartItems) {

            double price = item.getProduct().getPrice();
            int quantity = item.getQuantity();

            total = total + (price * quantity);
        }

        return total;
    }
    // Get Cart Items
    public List<CartItem> getCart(Long userId) {

        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return null;
        }

        Cart cart = cartRepository.findByUser(user).orElse(null);

        if (cart == null) {
            return List.of();
        }

        return cartItemRepository.findByCart(cart);
    }

    // Remove Cart Item
    public boolean removeFromCart(Long cartItemId) {

        if (cartItemRepository.existsById(cartItemId)) {
            cartItemRepository.deleteById(cartItemId);
            return true;
        }

        return false;
    }
}