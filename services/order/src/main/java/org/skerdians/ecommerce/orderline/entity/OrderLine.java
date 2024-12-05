package org.skerdians.ecommerce.orderline.entity;

import jakarta.persistence.*;
import lombok.*;
import org.skerdians.ecommerce.order.entity.Order;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "customer_line")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_line_seq")
    @SequenceGenerator(name = "customer_line_seq", sequenceName = "customer_line_seq", allocationSize = 50)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private Integer productId;
    private double quantity;
}