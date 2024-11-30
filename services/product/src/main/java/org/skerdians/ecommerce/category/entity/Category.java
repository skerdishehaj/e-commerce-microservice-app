package org.skerdians.ecommerce.category.entity;

import jakarta.persistence.*; // JPA annotations for ORM
import lombok.*; // Lombok annotations for boilerplate code reduction
import org.skerdians.ecommerce.product.entity.Product;

import java.math.BigDecimal; // BigDecimal for precise monetary calculations
import java.util.List; // List for holding multiple products

/**
 * Represents a category in the e-commerce platform.
 * <p>
 * This entity is mapped to a database table using JPA annotations.
 * Lombok annotations are used to reduce boilerplate code.
 * </p>
 *
 * @see Product
 * @see BigDecimal
 * @see List
 */
@AllArgsConstructor // Generates a constructor with 1 parameter for each field in the class
@NoArgsConstructor // Generates a no-argument constructor
@Builder // Implements the builder pattern for the class
@Getter // Generates getters for all fields
@Setter // Generates setters for all fields
@Entity // Specifies that the class is an entity and is mapped to a database table
public class Category {

    /**
     * The unique identifier for the category.
     * <p>
     * This field is automatically generated.
     * </p>
     */
    @Id // Specifies the primary key of an entity
    @GeneratedValue // Provides the specification of generation strategies for the values of primary keys
    private Integer id;

    /**
     * The name of the category.
     */
    private String name;

    /**
     * A brief description of the category.
     */
    private String description;

    /**
     * The list of products that belong to this category.
     * <p>
     * This is a one-to-many relationship with the {@link Product} entity.
     * When a category is deleted, all products that belong to that category will be deleted as well.
     * </p>
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE) // Specifies a one-to-many relationship and cascade type
    private List<Product> products;
}
