package org.skerdians.ecommerce.customer.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skerdians.ecommerce.customer.dto.CustomerRequest;
import org.skerdians.ecommerce.customer.dto.CustomerResponse;
import org.skerdians.ecommerce.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CustomerControllerTest {

    @Mock
    private CustomerService service;

    @InjectMocks
    private CustomerController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomer_returnsCustomerId() {
        CustomerRequest request = new CustomerRequest("1", "John", "Doe", "john.doe@example.com", null);
        when(service.createCustomer(any(CustomerRequest.class))).thenReturn("1");

        ResponseEntity<String> response = controller.createCustomer(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody());
    }

    @Test
    void updateCustomer_returnsAcceptedStatus() {
        CustomerRequest request = new CustomerRequest("1", "John", "Doe", "john.doe@example.com", null);

        ResponseEntity<Void> response = controller.updateCustomer(request);

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(service, times(1)).updateCustomer(request);
    }

    @Test
    void findAll_returnsListOfCustomers() {
        CustomerResponse response = new CustomerResponse("1", "John", "Doe", "john.doe@example.com", null);
        when(service.findAllCustomers()).thenReturn(List.of(response));

        ResponseEntity<List<CustomerResponse>> responseEntity = controller.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals("1", responseEntity.getBody().get(0).id());
    }

    @Test
    void existsById_returnsTrueWhenCustomerExists() {
        when(service.existsById("1")).thenReturn(true);

        ResponseEntity<Boolean> response = controller.existsById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    void existsById_returnsFalseWhenCustomerDoesNotExist() {
        when(service.existsById("1")).thenReturn(false);

        ResponseEntity<Boolean> response = controller.existsById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody());
    }

    @Test
    void findById_returnsCustomerWhenExists() {
        CustomerResponse response = new CustomerResponse("1", "John", "Doe", "john.doe@example.com", null);
        when(service.findById("1")).thenReturn(response);

        ResponseEntity<CustomerResponse> responseEntity = controller.findById("1");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("1", responseEntity.getBody().id());
    }

    @Test
    void deleteById_returnsAcceptedStatusWhenCustomerExists() {
        doNothing().when(service).deleteById("1");

        ResponseEntity<Void> response = controller.deleteById("1");

        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(service, times(1)).deleteById("1");
    }
}