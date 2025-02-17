package org.skerdians.ecommerce.product.contract;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.skerdians.ecommerce.ProductApplication;
import org.skerdians.ecommerce.product.controller.ProductController;
import org.skerdians.ecommerce.product.dto.ProductPurchaseResponse;
import org.skerdians.ecommerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;

@SpringBootTest(classes = ProductApplication.class)
public class BaseTestClass {
    @Autowired
    ProductController productController;

    @MockBean
    ProductService productService;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(productController);

        ProductPurchaseResponse response = ProductPurchaseResponse.builder()
                .description("Backlit gaming keyboard with customizable keys")
                .name("Gaming Keyboard 1")
                .price(BigDecimal.valueOf(129.99))
                .productId(101)
                .quantity(1)
                .build();

        Mockito.when(productService.purchaseProducts(Mockito.anyList()))
                .thenReturn(Collections.singletonList(response));
    }
}