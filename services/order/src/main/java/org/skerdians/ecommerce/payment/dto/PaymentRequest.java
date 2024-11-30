package org.skerdians.ecommerce.payment.dto;

import org.skerdians.ecommerce.customer.dto.CustomerResponse;
import org.skerdians.ecommerce.order.entity.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}