package org.skerdians.ecommerce.kafka;

import org.skerdians.ecommerce.customer.dto.CustomerResponse;
import org.skerdians.ecommerce.order.entity.PaymentMethod;
import org.skerdians.ecommerce.product.dto.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}