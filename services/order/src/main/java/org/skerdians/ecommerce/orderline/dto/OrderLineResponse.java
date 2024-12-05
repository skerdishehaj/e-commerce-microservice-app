package org.skerdians.ecommerce.orderline.dto;

public record OrderLineResponse(
        Integer id,
        Integer productId,
        double quantity
) {
}