package org.skerdians.ecommerce.product.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.skerdians.ecommerce.exception.ProductPurchaseException;
import org.skerdians.ecommerce.product.dto.ProductPurchaseRequest;
import org.skerdians.ecommerce.product.dto.ProductPurchaseResponse;
import org.skerdians.ecommerce.product.dto.ProductRequest;
import org.skerdians.ecommerce.product.dto.ProductResponse;
import org.skerdians.ecommerce.product.entity.Product;
import org.skerdians.ecommerce.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_createsAndReturnsProductId() {
        ProductRequest request = ProductRequest.builder()
                .id(1)
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .categoryId(1)
                .build();
        Product product = Product.builder()
                .id(1)
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .build();

        when(mapper.toProduct(request)).thenReturn(product);
        when(repository.save(any(Product.class))).thenReturn(product);

        Integer productId = service.createProduct(request);

        assertEquals(1, productId);
        verify(repository, times(1)).save(product);
    }

    @Test
    void findById_returnsProductResponseWhenProductExists() {
        Product product = Product.builder()
                .id(1)
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .build();
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

        when(repository.findById(1)).thenReturn(Optional.of(product));
        when(mapper.toProductResponse(product)).thenReturn(response);

        ProductResponse result = service.findById(1);

        assertEquals(1, result.id());
    }

    @Test
    void findById_throwsExceptionWhenProductNotFound() {
        when(repository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(1));
    }

    @Test
    void findAll_returnsListOfProductResponses() {
        Product product = Product.builder()
                .id(1)
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .build();
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

        when(repository.findAll()).thenReturn(List.of(product));
        when(mapper.toProductResponse(product)).thenReturn(response);

        List<ProductResponse> products = service.findAll();

        assertEquals(1, products.size());
        assertEquals(1, products.get(0).id());
    }

    @Test
    void purchaseProducts_purchasesProductsSuccessfully() {
        ProductPurchaseRequest request = ProductPurchaseRequest.builder()
                .productId(1)
                .quantity(2.0)
                .build();
        Product product = Product.builder()
                .id(1)
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .build();
        ProductPurchaseResponse response = ProductPurchaseResponse.builder()
                .productId(1)
                .name("Product1")
                .description("Description1")
                .price(new BigDecimal("100.00"))
                .quantity(2.0)
                .build();

        when(repository.findAllByIdInOrderById(List.of(1))).thenReturn(List.of(product));
        when(mapper.toproductPurchaseResponse(product, 2.0)).thenReturn(response);

        List<ProductPurchaseResponse> result = service.purchaseProducts(List.of(request));

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).productId());
        verify(repository, times(1)).save(product);
    }

    @Test
    void purchaseProducts_throwsExceptionWhenProductNotFound() {
        ProductPurchaseRequest request = ProductPurchaseRequest.builder()
                .productId(1)
                .quantity(2.0)
                .build();

        when(repository.findAllByIdInOrderById(List.of(1))).thenReturn(List.of());

        assertThrows(ProductPurchaseException.class, () -> service.purchaseProducts(List.of(request)));
    }

    @Test
    void purchaseProducts_throwsExceptionWhenInsufficientStock() {
        ProductPurchaseRequest request = ProductPurchaseRequest.builder()
                .productId(1)
                .quantity(20.0)
                .build();
        Product product = Product.builder()
                .id(1)
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .build();

        when(repository.findAllByIdInOrderById(List.of(1))).thenReturn(List.of(product));

        assertThrows(ProductPurchaseException.class, () -> service.purchaseProducts(List.of(request)));
    }
}