package org.skerdians.ecommerce.customer.dto;

import lombok.Builder;

@Builder
public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String email
) {

}