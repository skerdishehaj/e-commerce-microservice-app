package org.skerdians.ecommerce.customer.repository;

import org.skerdians.ecommerce.customer.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for managing customer entities in MongoDB.
 * This interface extends the {@link org.springframework.data.mongodb.repository.MongoRepository} interface provided by Spring Data MongoDB,
 * which provides CRUD operations and query methods for the {@link org.skerdians.ecommerce.customer.entity.Customer} entity.
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * @Autowired
 * private CustomerRepository customerRepository;
 *
 * public void someMethod() {
 *     Customer customer = new Customer();
 *     customerRepository.save(customer);
 * }
 * }</pre>
 *
 * @see org.springframework.data.mongodb.repository.MongoRepository
 * @see org.skerdians.ecommerce.customer.entity.Customer
 */
public interface CustomerRepository extends MongoRepository<Customer, String> {
}
