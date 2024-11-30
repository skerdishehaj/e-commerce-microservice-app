package org.skerdians.ecommerce.order.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.skerdians.ecommerce.customer.CustomerClient;
import org.skerdians.ecommerce.customer.dto.CustomerResponse;
import org.skerdians.ecommerce.exception.BusinessException;
import org.skerdians.ecommerce.kafka.OrderConfirmation;
import org.skerdians.ecommerce.kafka.OrderProducer;
import org.skerdians.ecommerce.order.dto.OrderRequest;
import org.skerdians.ecommerce.order.dto.OrderResponse;
import org.skerdians.ecommerce.order.entity.Order;
import org.skerdians.ecommerce.order.repository.OrderRepository;
import org.skerdians.ecommerce.orderline.dto.OrderLineRequest;
import org.skerdians.ecommerce.orderline.service.OrderLineService;
import org.skerdians.ecommerce.payment.PaymentClient;
import org.skerdians.ecommerce.payment.dto.PaymentRequest;
import org.skerdians.ecommerce.product.ProductClient;
import org.skerdians.ecommerce.product.dto.PurchaseRequest;
import org.skerdians.ecommerce.product.dto.PurchaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final OrderMapper mapper;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Transactional
    public Integer createOrder(OrderRequest request) {
        log.info("Order MS - OrderService class - Creating order for customer with ID: {}", request.customerId());
        CustomerResponse customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID = " + request.customerId()));
        log.info("Order MS - OrderService class - Customer found: {}", customer);

        ResponseEntity<List<PurchaseResponse>> responseEntity = this.productClient.purchaseProducts(request.products());
        List<PurchaseResponse> purchasedProducts = responseEntity.getBody();
        if (purchasedProducts == null || purchasedProducts.isEmpty()) {
            throw new BusinessException("An error occurred while processing the products purchase: " + responseEntity.getStatusCode());
        }

        Order order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        PaymentRequest paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return this.repository.findAll()
                .stream()
                .map(this.mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer id) {
        return this.repository.findById(id)
                .map(this.mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}