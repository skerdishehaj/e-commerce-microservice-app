package org.skerdians.ecommerce.customer.entity;

import lombok.*;
import org.springframework.validation.annotation.Validated;

// Generates a constructor with one parameter for each field in the class
@AllArgsConstructor
// Generates a no-argument constructor
@NoArgsConstructor
// Produces complex builder APIs for the class
@Builder
// Generates getter methods for all fields
@Getter
// Generates setter methods for all fields
@Setter
// Marks the class as validated, enabling Spring's validation mechanism
@Validated
public class Address {
    private String street;
    private String houseNumber;
    private String zipCode;
}
