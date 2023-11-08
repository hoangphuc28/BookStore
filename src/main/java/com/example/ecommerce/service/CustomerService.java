package com.example.ecommerce.service;
import com.example.ecommerce.model.Customer;
import com.example.ecommerce.model.Order;
import com.example.ecommerce.repo.CustomerRepo;
import com.example.ecommerce.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepository;

    // Create a new customer
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Get a customer by their ID
    public Customer getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        return customerOptional.orElse(null);
    }

    // Update an existing customer
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent()) {
            Customer existingCustomer = customerOptional.get();
            // Update fields of the existing customer with the new values
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setPhone(updatedCustomer.getPhone());
            existingCustomer.setAddress(updatedCustomer.getAddress());
            // Save the updated customer
            return customerRepository.save(existingCustomer);
        }
        return null; // Return null if the customer with the given ID does not exist
    }

    // Delete a customer by their ID
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}

