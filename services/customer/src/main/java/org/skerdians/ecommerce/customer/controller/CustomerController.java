package org.skerdians.ecommerce.customer.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.skerdians.ecommerce.customer.dto.CustomerRequest;
import org.skerdians.ecommerce.customer.dto.CustomerResponse;
import org.skerdians.ecommerce.customer.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing customer-related operations.
 * This controller handles HTTP requests for creating, updating, retrieving, and deleting customers.
 */
// Indicates that this class is a REST controller, capable of handling HTTP requests
@RestController
// Maps HTTP requests to /api/v1/customer to methods in this controller
@RequestMapping("/api/v1/customers")
// Generates a constructor with required arguments (final fields) for dependency injection
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    /**
     * Creates a new customer.
     *
     * @param request the customer request containing customer details
     * @return a ResponseEntity containing the ID of the created customer
     */
    @PostMapping
    public ResponseEntity<String> createCustomer(
            // @RequestBody binds the HTTP request body to the method parameter, @Valid ensures the request is validated
            @RequestBody @Valid CustomerRequest request
    ) {
        return ResponseEntity.ok(service.createCustomer(request));
    }

    /**
     * Updates an existing customer.
     *
     * @param request the customer request containing updated customer details
     * @return a ResponseEntity indicating the update status
     */
    @PutMapping
    public ResponseEntity<Void> updateCustomer(
            // @RequestBody binds the HTTP request body to the method parameter, @Valid ensures the request is validated
            @RequestBody @Valid CustomerRequest request
    ) {
        service.updateCustomer(request);
        return ResponseEntity.accepted().build();
    }

    /**
     * Retrieves all customers.
     *
     * @return a ResponseEntity containing a list of all customers
     */
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(service.findAllCustomers());
    }

    /**
     * Checks if a customer exists by ID.
     *
     * @param customerId the ID of the customer to check
     * @return a ResponseEntity containing a boolean indicating if the customer exists
     */
    @GetMapping("/exists/{customer-id}")
    public ResponseEntity<Boolean> existsById(
            // @PathVariable binds the method parameter to the value of the URI template variable
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(service.existsById(customerId));
    }

    /**
     * Retrieves a customer by ID.
     *
     * @param customerId the ID of the customer to retrieve
     * @return a ResponseEntity containing the customer details
     */
    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> findById(
            // @PathVariable binds the method parameter to the value of the URI template variable
            @PathVariable("customer-id") String customerId
    ) {
        return ResponseEntity.ok(service.findById(customerId));
    }

    /**
     * Deletes a customer by ID.
     *
     * @param customerId the ID of the customer to delete
     * @return a ResponseEntity indicating the deletion status
     */
    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> deleteById(
            // @PathVariable binds the method parameter to the value of the URI template variable
            @PathVariable("customer-id") String customerId
    ) {
        service.deleteById(customerId);
        return ResponseEntity.accepted().build();
    }
}
