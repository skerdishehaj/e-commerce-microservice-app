package org.skerdians.ecommerce.customer.service;

import org.junit.jupiter.api.Test;
import org.skerdians.ecommerce.customer.dto.CustomerRequest;
import org.skerdians.ecommerce.customer.dto.CustomerResponse;
import org.skerdians.ecommerce.customer.entity.Address;
import org.skerdians.ecommerce.customer.entity.Customer;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {

    private final CustomerMapper customerMapper = new CustomerMapper();

    @Test
    void toCustomer_withValidRequest_returnsCustomer() {
        CustomerRequest request = new CustomerRequest("1", "John", "Doe", "john.doe@example.com", new Address("123 Main St", "1A", "12345"));

        Customer customer = customerMapper.toCustomer(request);

        assertNotNull(customer);
        assertEquals("1", customer.getId());
        assertEquals("John", customer.getFirstName());
        assertEquals("Doe", customer.getLastName());
        assertEquals("john.doe@example.com", customer.getEmail());
        assertEquals("123 Main St", customer.getAddress().getStreet());
        assertEquals("1A", customer.getAddress().getHouseNumber());
        assertEquals("12345", customer.getAddress().getZipCode());
    }

    @Test
    void toCustomer_withNullRequest_returnsNull() {
        Customer customer = customerMapper.toCustomer(null);

        assertNull(customer);
    }

    @Test
    void toCustomerResponse_withValidCustomer_returnsCustomerResponse() {
        Customer customer = Customer.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .address(new Address("123 Main St", "1A", "12345"))
                .build();

        CustomerResponse response = customerMapper.toCustomerResponse(customer);

        assertNotNull(response);
        assertEquals("1", response.id());
        assertEquals("John", response.firstName());
        assertEquals("Doe", response.lastName());
        assertEquals("john.doe@example.com", response.email());
        assertEquals("123 Main St", response.address().getStreet());
        assertEquals("1A", response.address().getHouseNumber());
        assertEquals("12345", response.address().getZipCode());
    }

    @Test
    void toCustomerResponse_withNullCustomer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> customerMapper.toCustomerResponse(null));
    }
}