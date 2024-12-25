package org.skerdians.ecommerce.customer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.skerdians.ecommerce.customer.dto.CustomerRequest;
import org.skerdians.ecommerce.customer.dto.CustomerResponse;
import org.skerdians.ecommerce.customer.entity.Customer;
import org.skerdians.ecommerce.customer.repository.CustomerRepository;
import org.skerdians.ecommerce.exception.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * Service class for managing customer-related operations.
 * This class provides methods to {@code create}, {@code update}, {@code find}, and {@code delete} customers.
 */
@Slf4j
@Service // Marks this class as a Spring service component
@RequiredArgsConstructor // Generates a constructor with required arguments (final fields) for dependency injection
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper mapper;

    /**
     * Creates a new customer.
     *
     * @param request the customer request containing customer details
     * @return the ID of the created customer
     */
    public String createCustomer(CustomerRequest request) {
        log.info("Creating customer with email: {}", request.email());
        Customer customer = repository.save(mapper.toCustomer(request));
        log.info("Customer created with ID: {}", customer.getId());
        log.debug("Customer created: {}", customer);
        return customer.getId();
    }

    /**
     * Updates an existing customer.
     *
     * @param request the customer request containing updated customer details
     */
    public void updateCustomer(CustomerRequest request) {
        log.info("Updating customer with ID: {}", request.id());
        Customer customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update customer:: No customer found with the provided ID:: %s", request.id())
                ));

        mergeCustomer(customer, request);
        repository.save(customer);
        log.info("Customer updated with ID: {}", customer.getId());
        log.debug("Customer updated: {}", customer);
    }

    /**
     * Merges the customer details from the request into the existing customer entity.
     *
     * @param customer the existing customer entity
     * @param request  the customer request containing updated customer details
     */
    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())) {
            customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.lastName())) {
            customer.setLastName(request.lastName());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    /**
     * Retrieves all customers.
     *
     * @return a list of {@link CustomerResponse}
     */
    public List<CustomerResponse> findAllCustomers() {
        log.info("Retrieving all customers");
        List<CustomerResponse> customers = repository.findAll().stream()
                .map(mapper::toCustomerResponse)
                .collect(Collectors.toList());
        log.info("Customers retrieved: {}", customers.stream().map(CustomerResponse::id).collect(Collectors.toList()));
        log.debug("Customers retrieved: {}", customers);
        return customers;
    }

    /**
     * Checks if a customer exists by ID.
     *
     * @param customerId the ID of the customer
     * @return true if the customer exists, false otherwise
     */
    public Boolean existsById(String customerId) {
        log.info("Checking existence of customer with ID: {}", customerId);
        boolean exists = repository.findById(customerId).isPresent();
        log.info("Customer with ID: {} exists: {}", customerId, exists);
        return exists;
    }

    /**
     * Finds a customer by ID.
     *
     * @param customerId the ID of the customer
     * @return the customer response
     */
    public CustomerResponse findById(String customerId) {
        log.info("Finding customer by ID: {}", customerId);
        CustomerResponse response = repository.findById(customerId)
                .map(mapper::toCustomerResponse)
                .orElseThrow(() -> {
                    log.error("No customer found with the provided ID: {}", customerId);
                    return new CustomerNotFoundException(
                        format("No customer found with the provided ID:: %s", customerId)
                    );
                });
        log.info("Customer found with ID: {}", customerId);
        log.debug("Customer details: {}", response);
        return response;
    }

    /**
     * Deletes a customer by ID.
     *
     * @param customerId the ID of the customer
     */
    public void deleteById(String customerId) {
        log.info("Attempting to delete customer with ID: {}", customerId);
        if (!existsById(customerId)) {
            log.error("Cannot delete customer:: No customer found with the provided ID:: {}", customerId);
            throw new CustomerNotFoundException(
                    format("Cannot delete customer:: No customer found with the provided ID:: %s", customerId)
            );
        }
        repository.deleteById(customerId);
        log.info("Customer successfully deleted with ID: {}", customerId);
        log.debug("Customer deleted with ID: {}", customerId);
    }
}
