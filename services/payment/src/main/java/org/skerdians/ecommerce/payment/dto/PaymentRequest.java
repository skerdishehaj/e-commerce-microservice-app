package org.skerdians.ecommerce.payment.dto;

import lombok.Builder;
import org.skerdians.ecommerce.payment.entity.PaymentMethod;

import java.math.BigDecimal;

@Builder
public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}