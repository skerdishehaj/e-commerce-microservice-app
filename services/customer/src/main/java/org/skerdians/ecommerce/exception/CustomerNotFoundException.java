package org.skerdians.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Exception thrown when a customer is not found.
 * This exception extends {@link RuntimeException} and is used to indicate that a customer with the specified ID does not exist.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * throw new CustomerNotFoundException("Customer with ID 123 not found");
 * }</pre>
 *
 * @see RuntimeException
 */
// Ensures that the equals and hashCode methods are generated, calling the superclass methods
@EqualsAndHashCode(callSuper = true)
// Generates getters, setters, toString, equals, and hashCode methods
@Data
public class CustomerNotFoundException extends RuntimeException {
    private final String message;

    /**
     * Constructs a new {@code CustomerNotFoundException} with the specified detail message.
     *
     * @param message the detail message
     */
    public CustomerNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
