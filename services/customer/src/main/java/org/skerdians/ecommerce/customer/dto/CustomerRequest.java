package org.skerdians.ecommerce.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.skerdians.ecommerce.customer.entity.Address;

/**
 * Data Transfer Object (DTO) for customer requests.
 * This record encapsulates the data required to create or update a customer.
 *
 * @param id        the unique identifier of the customer
 * @param firstName the first name of the customer
 * @param lastName  the last name of the customer
 * @param email     the email address of the customer
 * @param address   the address of the customer
 */
public record CustomerRequest(
        String id,
        @NotNull(message = "Customer first name is required")
        String firstName,
        @NotNull(message = "Customer last name is required")
        String lastName,
        @NotNull(message = "Customer email is required")
        @Email(message = "Customer email must be a valid email address")
        String email,
        Address address
) {
}