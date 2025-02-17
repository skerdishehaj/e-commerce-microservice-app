package org.skerdians.ecommerce.payment.dto;

import lombok.Builder;
import org.skerdians.ecommerce.customer.dto.CustomerResponse;
import org.skerdians.ecommerce.order.entity.PaymentMethod;

import java.math.BigDecimal;

@Builder
public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}