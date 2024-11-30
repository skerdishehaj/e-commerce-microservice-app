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
    @GeneratedValue
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    private Integer productId;
    private double quantity;
}