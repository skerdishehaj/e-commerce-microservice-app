package org.skerdians.ecommerce.payment.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.skerdians.ecommerce.payment.controller.PaymentController;
import org.skerdians.ecommerce.payment.dto.PaymentRequest;
import org.skerdians.ecommerce.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class BaseTestClass {

    @Autowired
    PaymentController paymentController;

    @MockBean
    PaymentService paymentService;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(paymentController);
        Mockito.when(paymentService.createPayment(Mockito.any(PaymentRequest.class))).thenReturn(1953);
    }
}