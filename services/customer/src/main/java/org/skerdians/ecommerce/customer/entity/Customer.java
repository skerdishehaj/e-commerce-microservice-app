package org.skerdians.ecommerce.customer.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// Lombok annotation to generate a constructor with all class fields as parameters
@AllArgsConstructor
// Lombok annotation to generate a no-argument constructor
@NoArgsConstructor
// Lombok annotation to implement the builder pattern for the class
@Builder
// Lombok annotation to generate getter methods for all fields
@Getter
// Lombok annotation to generate setter methods for all fields
@Setter
// Spring Data MongoDB annotation to mark this class as a document to be stored in a MongoDB collection
@Document
public class Customer {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
}
