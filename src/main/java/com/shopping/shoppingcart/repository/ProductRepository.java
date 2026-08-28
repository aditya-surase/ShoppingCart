package com.shopping.shoppingcart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.shoppingcart.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByActiveTrue();

}