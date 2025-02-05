package org.skerdians.ecommerce.product.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skerdians.ecommerce.product.dto.ProductPurchaseRequest;
import org.skerdians.ecommerce.product.dto.ProductPurchaseResponse;
import org.skerdians.ecommerce.product.dto.ProductRequest;
import org.skerdians.ecommerce.product.dto.ProductResponse;
import org.skerdians.ecommerce.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private ProductService service;

    @InjectMocks
    private ProductController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_returnsProductId() {
        ProductRequest request = ProductRequest.builder()
                .id(1)
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .categoryId(1)
                .build();
        when(service.createProduct(any(ProductRequest.class))).thenReturn(1);

        ResponseEntity<Integer> response = controller.createProduct(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody());
    }

    @Test
    void purchaseProducts_returnsListOfProductPurchaseResponses() {
        ProductPurchaseRequest request = ProductPurchaseRequest.builder()
                .productId(1)
                .quantity(2.0)
                .build();
        ProductPurchaseResponse response = ProductPurchaseResponse.builder()
                .productId(1)
                .name("Product1")
                .description("Description1")
                .price(new BigDecimal("100.00"))
                .quantity(2.0)
                .build();
        when(service.purchaseProducts(any(List.class))).thenReturn(List.of(response));

        ResponseEntity<List<ProductPurchaseResponse>> responseEntity = controller.purchaseProducts(List.of(request));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1, responseEntity.getBody().get(0).productId());
    }

    @Test
    void findById_returnsProductWhenExists() {
        ProductResponse response = ProductResponse.builder()
                .id(1)
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .categoryId(1)
                .categoryName("Category1")
                .categoryDescription("CategoryDescription1")
                .build();
        when(service.findById(1)).thenReturn(response);

        ResponseEntity<ProductResponse> responseEntity = controller.findById(1);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().id());
    }

    @Test
    void findAll_returnsListOfProducts() {
        ProductResponse response = ProductResponse.builder()
                .id(1)
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .categoryId(1)
                .categoryName("Category1")
                .categoryDescription("CategoryDescription1")
                .build();
        when(service.findAll()).thenReturn(List.of(response));

        ResponseEntity<List<ProductResponse>> responseEntity = controller.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, responseEntity.getBody().size());
        assertEquals(1, responseEntity.getBody().get(0).id());
    }
}