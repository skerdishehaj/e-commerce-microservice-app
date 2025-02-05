package org.skerdians.ecommerce.customer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skerdians.ecommerce.customer.dto.CustomerRequest;
import org.skerdians.ecommerce.customer.dto.CustomerResponse;
import org.skerdians.ecommerce.customer.entity.Address;
import org.skerdians.ecommerce.customer.entity.Customer;
import org.skerdians.ecommerce.customer.repository.CustomerRepository;
import org.skerdians.ecommerce.exception.CustomerNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @Mock
    private CustomerMapper mapper;

    @InjectMocks
    private CustomerService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_createsAndReturnsCustomerId() {
        Address address = Address.builder().
                street("Main Street").
                houseNumber("123").
                zipCode("12345").
                build();
        CustomerRequest request = new CustomerRequest("1", "John", "Doe", "john.doe@example.com", address);
        Customer customer = new Customer("1", "John", "Doe", "john.doe@example.com", address);

        when(mapper.toCustomer(request)).thenReturn(customer);
        when(repository.save(any(Customer.class))).thenReturn(customer);

        String customerId = service.createCustomer(request);

        assertEquals("1", customerId);
        verify(repository, times(1)).save(customer);
    }

    @Test
    void updateCustomer_updatesExistingCustomer() {
        Address address = Address.builder().
                street("Main Street").
                houseNumber("123").
                zipCode("12345").
                build();
        CustomerRequest request = new CustomerRequest("1", "John", "Doe", "john.doe@example.com", address);
        Customer customer = new Customer("1", "John", "Doe", "john.doe@example.com", address);

        when(repository.findById("1")).thenReturn(Optional.of(customer));

        service.updateCustomer(request);

        verify(repository, times(1)).save(customer);
    }

    @Test
    void updateCustomer_throwsExceptionWhenCustomerNotFound() {
        CustomerRequest request = new CustomerRequest("1", "John", "Doe", "john.doe@example.com", null);

        when(repository.findById("1")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> service.updateCustomer(request));
    }

    @Test
    void findAllCustomers_returnsListOfCustomerResponses() {
        Customer customer = new Customer("1", "John", "Doe", "john.doe@example.com", null);
        CustomerResponse response = new CustomerResponse("1", "John", "Doe", "john.doe@example.com", null);

        when(repository.findAll()).thenReturn(List.of(customer));
        when(mapper.toCustomerResponse(customer)).thenReturn(response);

        List<CustomerResponse> customers = service.findAllCustomers();

        assertEquals(1, customers.size());
        assertEquals("1", customers.get(0).id());
    }

    @Test
    void existsById_returnsTrueWhenCustomerExists() {
        when(repository.findById("1")).thenReturn(Optional.of(new Customer()));

        boolean exists = service.existsById("1");

        assertTrue(exists);
    }

    @Test
    void existsById_returnsFalseWhenCustomerDoesNotExist() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        boolean exists = service.existsById("1");

        assertFalse(exists);
    }

    @Test
    void findById_returnsCustomerResponseWhenCustomerExists() {
        Customer customer = new Customer("1", "John", "Doe", "john.doe@example.com", null);
        CustomerResponse response = new CustomerResponse("1", "John", "Doe", "john.doe@example.com", null);

        when(repository.findById("1")).thenReturn(Optional.of(customer));
        when(mapper.toCustomerResponse(customer)).thenReturn(response);

        CustomerResponse result = service.findById("1");

        assertEquals("1", result.id());
    }

    @Test
    void findById_throwsExceptionWhenCustomerNotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> service.findById("1"));
    }

    @Test
    void deleteById_deletesCustomerWhenExists() {
        when(repository.findById("1")).thenReturn(Optional.of(new Customer()));

        service.deleteById("1");

        verify(repository, times(1)).deleteById("1");
    }

    @Test
    void deleteById_throwsExceptionWhenCustomerNotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> service.deleteById("1"));
    }
}