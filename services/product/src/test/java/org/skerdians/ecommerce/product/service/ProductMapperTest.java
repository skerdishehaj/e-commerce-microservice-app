package org.skerdians.ecommerce.product.service;

import org.junit.jupiter.api.Test;
import org.skerdians.ecommerce.category.entity.Category;
import org.skerdians.ecommerce.product.dto.ProductPurchaseResponse;
import org.skerdians.ecommerce.product.dto.ProductRequest;
import org.skerdians.ecommerce.product.dto.ProductResponse;
import org.skerdians.ecommerce.product.entity.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapper();

    @Test
    void toProduct_withValidRequest_returnsProduct() {
        ProductRequest request = ProductRequest.builder()
                .id(1)
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .categoryId(1)
                .build();

        Product product = productMapper.toProduct(request);

        assertNotNull(product);
        assertEquals(1, product.getId());
        assertEquals("Product1", product.getName());
        assertEquals("Description1", product.getDescription());
        assertEquals(10.0, product.getAvailableQuantity());
        assertEquals(new BigDecimal("100.00"), product.getPrice());
        assertEquals(1, product.getCategory().getId());
    }

    @Test
    void toProduct_withNullRequest_returnsNull() {
        Product product = productMapper.toProduct(null);

        assertNull(product);
    }

    @Test
    void toProductResponse_withValidProduct_returnsProductResponse() {
        Category category = Category.builder()
                .id(1)
                .name("Category1")
                .description("CategoryDescription1")
                .build();
        Product product = Product.builder()
                .id(1)
                .name("Product1")
                .description("Description1")
                .availableQuantity(10.0)
                .price(new BigDecimal("100.00"))
                .category(category)
                .build();

        ProductResponse response = productMapper.toProductResponse(product);

        assertNotNull(response);
        assertEquals(1, response.id());
        assertEquals("Product1", response.name());
        assertEquals("Description1", response.description());
        assertEquals(10.0, response.availableQuantity());
        assertEquals(new BigDecimal("100.00"), response.price());
        assertEquals(1, response.categoryId());
        assertEquals("Category1", response.categoryName());
        assertEquals("CategoryDescription1", response.categoryDescription());
    }

    @Test
    void toProductResponse_withNullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> productMapper.toProductResponse(null));
    }

    @Test
    void toProductPurchaseResponse_withValidProductAndQuantity_returnsProductPurchaseResponse() {
        Product product = Product.builder()
                .id(1)
                .name("Product1")
                .description("Description1")
                .price(new BigDecimal("100.00"))
                .build();

        ProductPurchaseResponse response = productMapper.toproductPurchaseResponse(product, 2.0);

        assertNotNull(response);
        assertEquals(1, response.productId());
        assertEquals("Product1", response.name());
        assertEquals("Description1", response.description());
        assertEquals(new BigDecimal("100.00"), response.price());
        assertEquals(2.0, response.quantity());
    }

    @Test
    void toProductPurchaseResponse_withNullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> productMapper.toproductPurchaseResponse(null, 2.0));
    }
}