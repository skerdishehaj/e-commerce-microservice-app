package org.skerdians.ecommerce.orderline.service;

import lombok.RequiredArgsConstructor;
import org.skerdians.ecommerce.orderline.dto.OrderLineRequest;
import org.skerdians.ecommerce.orderline.dto.OrderLineResponse;
import org.skerdians.ecommerce.orderline.entity.OrderLine;
import org.skerdians.ecommerce.orderline.repository.OrderLineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository repository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest request) {
        OrderLine order = mapper.toOrderLine(request);
        return repository.save(order).getId();
    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return repository.findAllByOrderId(orderId)
                .stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}