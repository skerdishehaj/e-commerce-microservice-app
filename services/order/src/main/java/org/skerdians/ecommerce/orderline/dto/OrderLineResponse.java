package org.skerdians.ecommerce.orderline.dto;

public record OrderLineResponse(
        Integer id,
        double quantity
) {
}