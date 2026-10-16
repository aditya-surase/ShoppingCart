package com.shopping.shoppingcart.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.shoppingcart.entity.Address;
import com.shopping.shoppingcart.service.AddressService;

@RestController
@RequestMapping("/users/{userId}/addresses")
@CrossOrigin(origins = "*")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Add a new address
    @PostMapping
    public ResponseEntity<Address> addAddress(
            @PathVariable Long userId,
            @RequestBody Address address) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(addressService.addAddress(userId, address));
    }

    // Get all addresses for a user
    @GetMapping
    public List<Address> getAddresses(@PathVariable Long userId) {
        return addressService.getAddresses(userId);
    }

    // Update an address
    @PutMapping("/{addressId}")
    public Address updateAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody Address address) {

        return addressService.updateAddress(userId, addressId, address);
    }

    // Delete an address
    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId) {

        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.noContent().build();
    }
}