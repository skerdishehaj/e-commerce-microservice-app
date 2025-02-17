package org.skerdians.ecommerce.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.skerdians.ecommerce.customer.entity.Address;

@Builder
public record CustomerResponse(
        String id,
        @NotNull(message = "Customer First Name is required")
        String firstName,
        @NotNull(message = "Customer Last Name is required")
        String lastName,
        @NotNull(message = "Customer Email is required")
        @Email(message = "Customer Email is not a valid email address")
        String email,
        Address address
) {
}
