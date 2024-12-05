package org.skerdians.ecommerce.orderline.service;

import org.skerdians.ecommerce.order.entity.Order;
import org.skerdians.ecommerce.orderline.dto.OrderLineRequest;
import org.skerdians.ecommerce.orderline.dto.OrderLineResponse;
import org.skerdians.ecommerce.orderline.entity.OrderLine;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine.builder()
                .productId(request.productId())
                .order(
                        Order.builder()
                                .id(request.orderId())
                                .build()
                )
                .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getId(),
                orderLine.getProductId(),
                orderLine.getQuantity()
        );
    }
}