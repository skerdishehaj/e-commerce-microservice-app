package org.skerdians.ecommerce.product.entity;

import jakarta.persistence.*; // JPA annotations for ORM
import lombok.*; // Lombok annotations for boilerplate code reduction
import org.skerdians.ecommerce.category.entity.Category;

import java.math.BigDecimal; // BigDecimal for precise monetary calculations

/**
 * Represents a product in the e-commerce platform.
 * <p>
 * This entity is mapped to a database table using JPA annotations.
 * Lombok annotations are used to reduce boilerplate code.
 * </p>
 *
 * @see Category
 * @see BigDecimal
 */
// Generates a constructor with 1 parameter for each field in the class
@AllArgsConstructor
// Generates a no-argument constructor
@NoArgsConstructor
// Implements the builder pattern for the class
@Builder
// Generates getters for all fields
@Getter
// Generates setters for all fields
@Setter
// Specifies that the class is an entity and is mapped to a database table
@Entity
public class Product {

    /**
     * The unique identifier for the product.
     * <p>
     * This field is automatically generated.
     * </p>
     */
    @Id // Specifies the primary key of an entity
    @GeneratedValue // Provides the specification of generation strategies for the values of primary keys
    private Integer id;

    /**
     * The name of the product.
     */
    private String name;

    /**
     * A brief description of the product.
     */
    private String description;

    /**
     * The quantity of the product available in stock.
     */
    private double availableQuantity;

    /**
     * The price of the product.
     * <p>
     * BigDecimal is used for precise monetary calculations.
     * </p>
     */
    private BigDecimal price;

    /**
     * The category to which the product belongs.
     * <p>
     * This is a many-to-one relationship with the {@link Category} entity.
     * </p>
     */
    @ManyToOne // Specifies a many-to-one relationship
    @JoinColumn(name = "category_id") // Specifies the foreign key column
    private Category category;
}
