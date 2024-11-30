package org.skerdians.ecommerce.customer.service;

import lombok.RequiredArgsConstructor;
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
        Customer customer = repository.save(mapper.toCustomer(request));
        return customer.getId();
    }

    /**
     * Updates an existing customer.
     *
     * @param request the customer request containing updated customer details
     */
    public void updateCustomer(CustomerRequest request) {
        Customer customer = repository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("Cannot update customer:: No customer found with the provided ID:: %s", request.id())
                ));

        mergeCustomer(customer, request);
        repository.save(customer);
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
        return repository.findAll().stream()
                .map(mapper::toCustomerResponse)
                .collect(Collectors.toList());
    }

    /**
     * Checks if a customer exists by ID.
     *
     * @param customerId the ID of the customer
     * @return true if the customer exists, false otherwise
     */
    public Boolean existsById(String customerId) {
        return repository.findById(customerId).isPresent();
    }

    /**
     * Finds a customer by ID.
     *
     * @param customerId the ID of the customer
     * @return the customer response
     */
    public CustomerResponse findById(String customerId) {
        return repository.findById(customerId)
                .map(mapper::toCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException(
                        format("No customer found with the provided ID:: %s", customerId)
                ));
    }

    /**
     * Deletes a customer by ID.
     *
     * @param customerId the ID of the customer
     */
    public void deleteById(String customerId) {
        if (!existsById(customerId)) {
            throw new CustomerNotFoundException(
                    format("Cannot delete customer:: No customer found with the provided ID:: %s", customerId)
            );
        }
        repository.deleteById(customerId);
    }
}
