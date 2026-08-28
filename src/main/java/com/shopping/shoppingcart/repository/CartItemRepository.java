package com.shopping.shoppingcart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.shoppingcart.entity.Cart;
import com.shopping.shoppingcart.entity.CartItem;
import com.shopping.shoppingcart.entity.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCart(Cart cart);

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);
}