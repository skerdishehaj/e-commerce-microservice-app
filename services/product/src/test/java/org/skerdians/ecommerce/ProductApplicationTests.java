package org.skerdians.ecommerce;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skerdians.ecommerce.product.dto.ProductPurchaseRequest;
import org.skerdians.ecommerce.product.dto.ProductRequest;
import org.skerdians.ecommerce.product.repository.ProductRepository;
import org.skerdians.ecommerce.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@AutoConfigureMockMvc
class ProductApplicationTests {

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:13.3")
            .withDatabaseName("ecommerce")
            .withUsername("postgres")
            .withPassword("password");

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest productRequest = getProductRequest();
        String productRequestJson = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productRequestJson))
                .andExpect(status().isOk());
        assertEquals(1, productRepository.findAll().size());
    }

    @Test
    void shouldPurchaseProducts() throws Exception {
        // Create products first
        ProductRequest productRequest1 = ProductRequest.builder()
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .categoryId(1)
                .build();
        ProductRequest productRequest2 = ProductRequest.builder()
                .name("Product2")
                .description("Description2")
                .availableQuantity(15.0)
                .price(new BigDecimal("150.00"))
                .categoryId(1)
                .build();
        Integer productId1 = productService.createProduct(productRequest1);
        Integer productId2 = productService.createProduct(productRequest2);

        // Purchase products
        List<ProductPurchaseRequest> purchaseRequests = List.of(
                new ProductPurchaseRequest(productId1, 2),
                new ProductPurchaseRequest(productId2, 3)
        );
        String purchaseRequestJson = objectMapper.writeValueAsString(purchaseRequests);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(purchaseRequestJson))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindAllProducts() throws Exception {
        // Create products first
        ProductRequest productRequest1 = ProductRequest.builder()
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .categoryId(1)
                .build();
        ProductRequest productRequest2 = ProductRequest.builder()
                .name("Product2")
                .description("Description2")
                .availableQuantity(15.0)
                .price(new BigDecimal("150.00"))
                .categoryId(1)
                .build();
        productService.createProduct(productRequest1);
        productService.createProduct(productRequest2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindProductById() throws Exception {
        ProductRequest productRequest = getProductRequest();
        Integer id = productService.createProduct(productRequest);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private ProductRequest getProductRequest() {
        return ProductRequest.builder()
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .categoryId(1)
                .build();
    }
}
