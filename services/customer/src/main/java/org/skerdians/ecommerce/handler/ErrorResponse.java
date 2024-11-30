package org.skerdians.ecommerce.handler;

import java.util.Map;

/**
 * A record that represents an error response.
 * This class is used to encapsulate validation errors in a structured format.
 *
 * @param errors a map containing field names as keys and error messages as values
 * @see java.util.Map
 */
public record ErrorResponse(
        Map<String, String> errors
) {
}