package com.shopping.shoppingcart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopping.shoppingcart.entity.Product;
import com.shopping.shoppingcart.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Add Product
    public Product addProduct(Product product) {

        product.setActive(true);

        return productRepository.save(product);
    }

    // Get All Products
    public List<Product> getAllProducts() {

        return productRepository.findByActiveTrue();
    }

    // Get Product By ID
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    // Update Product
    public Product updateProduct(Long id, Product product) {

        Product existingProduct =
                productRepository.findById(id).orElse(null);

        if (existingProduct == null) {
            return null;
        }

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setImage(product.getImage());

        return productRepository.save(existingProduct);
    }

    // Delete Product
    public boolean deleteProduct(Long id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return false;
        }

        product.setActive(false);

        productRepository.save(product);

        return true;
    }
}