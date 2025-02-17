package org.skerdians.ecommerce.customer.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.skerdians.ecommerce.CustomerApplication;
import org.skerdians.ecommerce.customer.controller.CustomerController;
import org.skerdians.ecommerce.customer.dto.CustomerResponse;
import org.skerdians.ecommerce.customer.entity.Address;
import org.skerdians.ecommerce.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(classes = CustomerApplication.class)
public class BaseTestClass {
    @Autowired
    CustomerController customerController;

    @MockBean
    CustomerService customerService;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(customerController);

        Address address = Address.builder()
                .street("123 Main St")
                .houseNumber("1")
                .zipCode("12345")
                .build();
        CustomerResponse customerResponse = CustomerResponse.builder()
                .id("1")
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@email.com")
                .address(address)
                .build();

        Mockito.when(customerService.findById("1")).thenReturn(customerResponse);
    }
}
