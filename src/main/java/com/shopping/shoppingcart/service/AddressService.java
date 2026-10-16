package com.shopping.shoppingcart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.shoppingcart.entity.Address;
import com.shopping.shoppingcart.entity.User;
import com.shopping.shoppingcart.repository.AddressRepository;
import com.shopping.shoppingcart.repository.UserRepository;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(
            AddressRepository addressRepository,
            UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    // Add a new address
    public Address addAddress(Long userId, Address address) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        address.setId(null);
        address.setUser(user);

        return addressRepository.save(address);
    }

    // Get all addresses for a customer
    public List<Address> getAddresses(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }

        return addressRepository.findByUserId(userId);
    }

    // Update an existing address
    public Address updateAddress(
            Long userId, Long addressId, Address updatedAddress) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException(
                    "You cannot update another user's address");
        }

        address.setAddressType(updatedAddress.getAddressType());
        address.setFullAddress(updatedAddress.getFullAddress());
        address.setCity(updatedAddress.getCity());
        address.setState(updatedAddress.getState());
        address.setPincode(updatedAddress.getPincode());

        return addressRepository.save(address);
    }

    // Delete an address
    @Transactional
    public void deleteAddress(Long userId, Long addressId) {

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        if (!address.getUser().getId().equals(userId)) {
            throw new RuntimeException(
                    "You cannot delete another user's address");
        }

        addressRepository.delete(address);
    }
}
