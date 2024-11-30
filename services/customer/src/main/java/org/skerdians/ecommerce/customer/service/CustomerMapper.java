package org.skerdians.ecommerce.customer.service;

import org.skerdians.ecommerce.customer.dto.CustomerRequest;
import org.skerdians.ecommerce.customer.dto.CustomerResponse;
import org.skerdians.ecommerce.customer.entity.Customer;
import org.springframework.stereotype.Service;

/**
 * Mapper class for converting between {@link CustomerRequest}, {@link CustomerResponse}, and {@link Customer} entities.
 * This class provides methods to map data transfer objects (DTOs) to entities and vice versa.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * @Autowired
 * private CustomerMapper customerMapper;
 *
 * public void someMethod() {
 *     CustomerRequest request = new CustomerRequest();
 *     Customer customer = customerMapper.toCustomer(request);
 *     CustomerResponse response = customerMapper.toCustomerResponse(customer);
 * }
 * }</pre>
 *
 * @see org.skerdians.ecommerce.customer.dto.CustomerRequest
 * @see org.skerdians.ecommerce.customer.dto.CustomerResponse
 * @see org.skerdians.ecommerce.customer.entity.Customer
 */
// Marks this class as a Spring service component, making it eligible for component scanning and dependency injection
@Service
public class CustomerMapper {

    /**
     * Converts a {@link CustomerRequest} DTO to a {@link Customer} entity.
     *
     * @param request the customer request containing customer details
     * @return the Customer entity
     */
    public Customer toCustomer(CustomerRequest request) {
        if (request == null) {
            return null;
        }
        return Customer.builder()
                .id(request.id())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .address(request.address())
                .build();
    }

    /**
     * Converts a {@link Customer} entity to a {@link CustomerResponse} DTO.
     *
     * @param customer the {@link Customer} entity
     * @return the {@link CustomerResponse} DTO
     */
    public CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
